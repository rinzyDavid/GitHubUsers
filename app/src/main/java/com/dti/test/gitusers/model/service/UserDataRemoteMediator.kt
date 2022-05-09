package com.dti.test.gitusers.model.service

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.dti.test.gitusers.common.di.IoScheduler
import com.dti.test.gitusers.persistence.repository.LocalUserRepository
import com.dti.test.gitusers.network.api.GitUserApiService
import com.dti.test.gitusers.persistence.db.UsersRoomDb
import com.dti.test.gitusers.persistence.entity.UserEntity
import com.dti.test.gitusers.persistence.entity.UserRemoteKey
import com.dti.test.gitusers.persistence.mapper.UserDataMapper
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import java.io.InvalidObjectException
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class UserDataRemoteMediator @Inject constructor (
    private val usersRoomDb: UsersRoomDb,
    private val apiService: GitUserApiService,
    @IoScheduler private val scheduler: Scheduler,
    private val localRepo: LocalUserRepository,
    private val mapper:UserDataMapper
        ):RxRemoteMediator<Int,UserEntity>(){

    override fun loadSingle(loadType: LoadType, state: PagingState<Int, UserEntity>): Single<MediatorResult> {

       var total = 0

        return Single.just(loadType)
            .subscribeOn(scheduler)
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)

                        remoteKeys?.nextKey?.minus(1) ?: 1
                    }
                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.prevKey ?: INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                            ?: throw InvalidObjectException("Result is empty")

                        remoteKeys.nextKey ?: INVALID_PAGE
                    }
                }
            }
            .flatMap {page->

                if (page == INVALID_PAGE) {
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                } else {

                    apiService.fetchGitUsers("lagos",page)
                        .subscribeOn(scheduler)
                        .map {
                            total = it.total
                            mapper.dtoToEntityList.map(it.data) }
                        .map { insertToDb(page,loadType,it,total) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = total == page) }
                        .onErrorReturn { MediatorResult.Error(it) }
                }

            }
            .onErrorReturn { MediatorResult.Error(it) }



    }

    @Suppress("DEPRECATION")
    private fun insertToDb(page: Int, loadType: LoadType, data: List<UserEntity>,total:Int): List<UserEntity> {
        usersRoomDb.beginTransaction()

        try {
            if (loadType == LoadType.REFRESH) {
                localRepo.clearKeys()
                localRepo.deleteAll()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (total == page) null else page + 1
            val keys = data.map {
                UserRemoteKey(it.userId,prevKey,nextKey)
            }
            localRepo.createRemoteKeys(keys)
            localRepo.insertAll(data)
            usersRoomDb.setTransactionSuccessful()

        } finally {
            usersRoomDb.endTransaction()
        }

        return data
    }


    private fun getRemoteKeyForLastItem(state: PagingState<Int, UserEntity>): UserRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            localRepo.getKeyByUserId(repo.userId)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, UserEntity>): UserRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { user ->
            localRepo.getKeyByUserId(user.userId)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UserEntity>): UserRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.userId?.let { id ->
                localRepo.getKeyByUserId(id)
            }
        }
    }

    companion object {
        const val INVALID_PAGE = -1
    }

}