package com.dti.test.gitusers.model.service

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.dti.test.gitusers.common.di.IoScheduler
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.network.api.GitUserApiService
import com.dti.test.gitusers.persistence.db.UsersRoomDb
import com.dti.test.gitusers.persistence.entity.UserEntity
import com.dti.test.gitusers.persistence.entity.UserRemoteKey
import com.dti.test.gitusers.persistence.mapper.UserDataMapper
import com.dti.test.gitusers.persistence.repository.UserRepository
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * This mediator class reactively loads more users from github service when the user
 * scrolls to the end of the list in local cache.
 * It makes use of a local remote key data which helps to determine which page to load as the
 * user scrolls
 */
@OptIn(ExperimentalPagingApi::class)
class UserDataRemoteMediator @Inject constructor (
    private val usersRoomDb: UsersRoomDb,
    private val apiService: GitUserApiService,
    @IoScheduler private val scheduler: Scheduler,
    private val localRepo: UserRepository,
    private val mapper:UserDataMapper
        ):RxRemoteMediator<Int,UserEntity>(){



    override fun loadSingle(loadType: LoadType, state: PagingState<Int, UserEntity>): Single<MediatorResult> {

        return Single.just(loadType)
            .subscribeOn(scheduler)
            .map {
                when (it) {
                    LoadType.REFRESH -> {
                        //Return 1 so which will load the first page from network and
                        // clear local cache to update data
                        1
                    }
                    LoadType.PREPEND -> {
                        //We dont need to add any data at the begining of our list
                         INVALID_PAGE
                    }
                    LoadType.APPEND -> {

                         var remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                        remoteKeys?.nextKey?: 1


                    }
                }
            }
            .flatMap {page ->

                if(page == INVALID_PAGE){
                    Single.just(MediatorResult.Success(endOfPaginationReached = true))
                }
                else {
                    apiService.fetchGitUsers("lagos", page as Int)
                        .subscribeOn(scheduler)
                        .map { mapper.dtoToEntityList.map(it.data) }
                        .map { insertToDb(page as Int,loadType,it,0) }
                        .map<MediatorResult> { MediatorResult.Success(endOfPaginationReached = it.isEmpty()) }
                        .onErrorReturn {
                            it.printStackTrace()
                            MediatorResult.Error(it)
                        }
                }

            }
            .onErrorReturn {
                it.printStackTrace()
                MediatorResult.Error(it) }





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
            val nextKey = if (data.isEmpty()) null else page + 1
            val keys = data.map {
               // localRepo.deleteUserById(it.id!!)
                UserRemoteKey(it.id!!,prevKey,nextKey)
            }

            localRepo.createRemoteKeys(keys)
            localRepo.insertAll(data)
            usersRoomDb.setTransactionSuccessful()

        }catch (e:Exception){
            e.printStackTrace()
        }
        finally {
            usersRoomDb.endTransaction()
        }

        return data
    }

    fun fetchUser(username:String):Single<GitUser>{

        return apiService.fetchUserDetails(username)
            .map { mapper.dtoToEntityMapper.map(it) }
            .map { updateUser(it) }
            .map { mapper.entityToModelMapper.map(it) }

    }

    @Suppress("DEPRECATION")
    private fun updateUser(userEntity: UserEntity):UserEntity {
        usersRoomDb.beginTransaction()
        try {
            //userEntity.isDataComplete = true
            val entity = localRepo.fetchUser(userEntity.id!!)
            userEntity.userId = entity.userId
            userEntity.isDataComplete = true
            localRepo.updateUser(userEntity)
        }finally {
            usersRoomDb.endTransaction()
        }
        return userEntity
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UserEntity>): UserRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                println("userId == $id")
                localRepo.getKeyByUserId(id)
            }
        }
    }

    companion object {
        const val INVALID_PAGE = -1
    }

}