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
 * This remote mediator class helps to load more pages and update the local cache
 * as the user scrolls through.
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
                        println("paging refresh called")

                    // since it is the first time the user is querying the
                        //page, return 1 to load the first page from the network
                        1
                    }
                    LoadType.PREPEND -> {
                        println("paging prepend called")
                        // we don't need to prepend data so we will return invalid_page

                         INVALID_PAGE
                    }
                    LoadType.APPEND -> {
                    //called when the user scrolls close to the end of last page to
                        //load more pages using value from local cache key
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
                    println("fetch data for page $page")

                    //fetches data from the backend service and updates local cache
                    apiService.fetchGitUsers("lagos", page as Int)
                        .subscribeOn(scheduler)
                        .map {

                            println("mapping to entity")
                            mapper.dtoToEntityList.map(it.data) }
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

        println("data saving in database ${data.size}")
        //Check if it is first time load, clear the
        //data and key cache
        try {
            if (loadType == LoadType.REFRESH) {
                localRepo.clearKeys()
                localRepo.deleteAll()
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (data.isEmpty()) null else page + 1
            val keys = data.map {
                println("id from entity ${it.userId}")

               // localRepo.deleteUserById(it.id!!)
                UserRemoteKey(it.id!!,prevKey,nextKey)
            }
            println("keys to save $keys")
            localRepo.createRemoteKeys(keys)
            localRepo.insertAll(data)
            println("data saved.....")
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
            userEntity.isDataComplete = true
            val entity = localRepo.fetchUser(userEntity.id!!)
            println("updating user....... $userEntity")
            userEntity.userId = entity.userId
            println("updating user....... $userEntity")
            localRepo.updateUser(userEntity)
        }finally {
            usersRoomDb.endTransaction()
        }
        return userEntity
    }


    private fun getRemoteKeyForLastItem(state: PagingState<Int, UserEntity>): UserRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            println("userId == ${repo.id}")
            localRepo.getKeyByUserId(repo.id!!)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, UserEntity>): UserRemoteKey? {
        println("calling prepend helper ${state.pages.firstOrNull()?.data?.size}")
        state.pages.forEach {
            println("data for prepend is ${it.data}")
        }


        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { user ->
            println("userId == ${user.id}")
            localRepo.getKeyByUserId(user.id!!)
        }
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