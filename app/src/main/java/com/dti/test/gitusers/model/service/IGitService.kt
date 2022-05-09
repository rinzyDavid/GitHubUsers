package com.dti.test.gitusers.model.service

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.model.domain.Result
import io.reactivex.rxjava3.core.Flowable

interface IGitService {

    fun fetchUsers(): Flowable<PagingData<GitUser>>
    fun fetchFavourite():LiveData<List<GitUser>>
    fun updateUser(user:GitUser,isFavourite:Boolean,callback:()->Unit)
    fun removeFromFavourite(user:GitUser,callback:()->Unit)
    fun removeAllFromFavourite(callback:()->Unit)
}