package com.dti.test.gitusers.model.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import com.dti.test.gitusers.common.di.IoScheduler
import com.dti.test.gitusers.common.di.MainScheduler
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.model.domain.ResultData
import com.dti.test.gitusers.model.domain.SimpleResult
import com.dti.test.gitusers.persistence.mapper.UserDataMapper
import com.dti.test.gitusers.persistence.repository.FavouriteRepo
import com.dti.test.gitusers.persistence.repository.UserRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject


/**
A concrete implementation of the GitService through which the application can access data
 both from local room database and network (with the help of a remote mediator object)
 */
class IGitServiceImpl @Inject constructor(
    val localRepo: UserRepository,
    val remoteMediator: UserDataRemoteMediator,
    val mapper: UserDataMapper,
    val favouriteRepo: FavouriteRepo,
    @IoScheduler val scheduler: Scheduler,
    @MainScheduler val mainScheduler: Scheduler
): IGitService {

    private val disposable = CompositeDisposable()

/*
This method returns  flowable streams of data from a Pager object
which uses local room database as the source of truth and cache while
using the mediator object to query the backend network when the user
has scrolled to the end of the list in local cache
 */
    @OptIn(ExperimentalPagingApi::class)
    override fun fetchUsers(): Flowable<PagingData<GitUser>> {

    return Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = true,
            maxSize = 20,
            prefetchDistance = 2,
            initialLoadSize = 20
        ),
        remoteMediator = remoteMediator,
        pagingSourceFactory = { localRepo.listAllUsers() }
    )
        .flowable.map {
        mapper.entityLvDataToModelMapper.map(it) }

    }

    override fun fetchFavourite(): LiveData<List<GitUser>> {
        return Transformations.map(favouriteRepo.fetchFavourites()) { list ->
            mapper.favEntityToModelMapper.map(list)
        }
    }

    /*
    This method uses a boolean value from the user object to determine
    whether to fetch the user's complete details from local cache or
    use the remote mediator to fetch and save user's complete details
     */
    override fun fetchUserDetails(username: String, isDataComplete: Boolean): LiveData<SimpleResult<GitUser>> {
        println("is data complete    $isDataComplete")

        if (isDataComplete){
            return Transformations.map(localRepo.fetchUser(username)){
                ResultData.Success(mapper.entityToModelMapper.map(it))
            }
        }
        else{
            val data = MutableLiveData<ResultData<GitUser,Throwable>>()
            disposable.add(

                remoteMediator.fetchUser(username)
                    .subscribeOn(scheduler)
                    .observeOn(mainScheduler)
                    .subscribe { it,e->
                        if (e!=null){
                           data.value = ResultData.Failure(e)
                        }
                        else{
                            data.value = ResultData.Success<GitUser>(it)
                        }
                    }
            )
            return data
        }

    }



    override fun addFavourite(user: GitUser) {

       disposable.add(
           Completable.fromAction {
               val fav = mapper.favModelToEntityMapper.map(user)
               favouriteRepo.createFavourite(fav)
           }.subscribeOn(scheduler)
               .subscribe {

               }
       )

    }



    override fun removeFromFavourite(user: GitUser) {

       disposable.add(
           Completable.fromAction {

               val fav = mapper.favModelToEntityMapper.map(user)
               favouriteRepo.removeFavourite(fav)

           }.subscribeOn(scheduler)
               .subscribe {
                   //callback()
               }
       )
    }

    override fun removeAllFromFavourite() {

       disposable.add(
           Completable.fromAction {
               favouriteRepo.removeAllFavourites()
           }.subscribeOn(scheduler)
               .subscribe {
                  // callback()
               }
       )
    }

    override fun cleanup() {
       if (!disposable.isDisposed)
           disposable.dispose()
    }
}