package com.dti.test.gitusers.model.repository

import androidx.lifecycle.LiveData
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.model.domain.Result

interface IGitService {

    fun fetchUsers(page:Int):LiveData<Result<List<GitUser>>>
    fun fetchFavourite(page:Int):LiveData<Result<List<GitUser>>>
    fun updateUser(user:GitUser,isFavourite:Boolean)
    fun removeFromFavourite(user:GitUser)
    fun removeAllFromFavourite()
}