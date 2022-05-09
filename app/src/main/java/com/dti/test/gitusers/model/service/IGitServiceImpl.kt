package com.dti.test.gitusers.model.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.dti.test.gitusers.common.di.IoScheduler
import com.dti.test.gitusers.common.di.MainScheduler
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.persistence.repository.LocalUserRepository
import com.dti.test.gitusers.persistence.mapper.UserDataMapper
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject


/**

 */
class IGitServiceImpl @Inject constructor(
    val localRepo: LocalUserRepository,
    val remoteMediator: UserDataRemoteMediator,
    val mapper: UserDataMapper,
    @IoScheduler val scheduler: Scheduler,
    @MainScheduler val mainScheduler: Scheduler
): IGitService {

/*

 */
    @OptIn(ExperimentalPagingApi::class)
    override fun fetchUsers(): Flowable<PagingData<GitUser>> {
    return Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = true,
            maxSize = 30,
            prefetchDistance = 5,
            initialLoadSize = 40),
        remoteMediator = remoteMediator,
        pagingSourceFactory = { localRepo.listAllUsers() }
    ).flowable.map { mapper.entityLvDataToModelMapper.map(it) }

    }

    override fun fetchFavourite(): LiveData<List<GitUser>> {
        return Transformations.map(localRepo.listFavourites()) { list ->
            mapper.entityListToModelMapper.map(list)
        }
    }

    override fun updateUser(user: GitUser, isFavourite: Boolean,callback:()->Unit) {

        Completable.fromAction {
            localRepo.updateUser(user)
        }.subscribeOn(scheduler)
            .observeOn(mainScheduler)
            .subscribe {
                callback()
            }

    }

    override fun removeFromFavourite(user: GitUser,callback:()->Unit) {

        Completable.fromAction {
            localRepo.deleteUser(user)
        }.subscribeOn(scheduler)
            .observeOn(mainScheduler)
            .subscribe {
                callback()
            }
    }

    override fun removeAllFromFavourite(callback:()->Unit) {

        Completable.fromAction {
            localRepo.clearFavourites()
        }.subscribeOn(scheduler)
            .observeOn(mainScheduler)
            .subscribe {
                callback()
            }
    }
}