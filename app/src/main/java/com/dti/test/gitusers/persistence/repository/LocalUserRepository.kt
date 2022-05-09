package com.dti.test.gitusers.persistence.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.network.dto.UserDto
import com.dti.test.gitusers.persistence.entity.UserEntity
import com.dti.test.gitusers.persistence.entity.UserRemoteKey

interface LocalUserRepository {
    fun saveUser(user:GitUser)
    fun updateUser(user: GitUser)
    fun listAllUsers(): PagingSource<Int, UserEntity>
    fun listFavourites():LiveData<List<UserEntity>>
    fun deleteUser(user: GitUser)
    fun deleteAll()
    fun insertAll(users:List<UserEntity>)
    fun createRemoteKeys(keys:List<UserRemoteKey>)
    fun getKeyByUserId(userId:Long):UserRemoteKey
    fun clearKeys()
    fun clearFavourites()
}