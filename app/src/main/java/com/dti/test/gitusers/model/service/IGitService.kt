package com.dti.test.gitusers.model.service

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.model.domain.SimpleResult
import io.reactivex.rxjava3.core.Flowable

interface IGitService {

    fun fetchUsers(): Flowable<PagingData<GitUser>>
    fun fetchFavourite():LiveData<List<GitUser>>
    fun fetchUserDetails(username:String,isDataComplete:Boolean):LiveData<SimpleResult<GitUser>>
    fun addFavourite(user:GitUser)
    fun removeFromFavourite(user:GitUser)
    fun removeAllFromFavourite()
    fun cleanup()
}