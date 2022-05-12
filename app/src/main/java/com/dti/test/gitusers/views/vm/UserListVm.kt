package com.dti.test.gitusers.views.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava3.cachedIn
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.model.service.IGitService
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@HiltViewModel
class UserListVm @Inject constructor(
    val iGitService: IGitService
):ViewModel() {

    var username:String? = null
    var isUserDetailsComplete:Boolean? = null
    var user:GitUser?= null
   // private var _liveDetails = MutableLiveData<GitUser>()
   // var userDetails:LiveData<GitUser> = _liveDetails

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchGitUsers():Flowable<PagingData<GitUser>> = iGitService.fetchUsers()
        .cachedIn(viewModelScope)


    fun fetchUserDetails() = iGitService.fetchUserDetails(username!!,isUserDetailsComplete!!)

    fun addFavourite(){
        println("updating user from details $user")
        user?.let {
           it.isFavourite = true
            iGitService.addFavourite(it)
        }
    }

    fun addFavourite(user:GitUser){
        iGitService.addFavourite(user)
    }

    fun fetchFavourites():LiveData<List<GitUser>> = iGitService.fetchFavourite()

   fun removeFavourite(user:GitUser){
       iGitService.removeFromFavourite(user)
   }

    fun removeAllFavourites(){
        iGitService.removeAllFromFavourite()
    }

}