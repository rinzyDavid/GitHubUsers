package com.dti.test.gitusers.persistence.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.persistence.entity.UserEntity
import com.dti.test.gitusers.persistence.entity.UserRemoteKey

/**
 * A User repository contract for accessing UserEntity and UserRemoteKey objects
 * from local Room database
 */

interface UserRepository {
    fun saveUser(user:GitUser)
    fun updateUser(user: GitUser)
    fun updateUser(user:UserEntity)
    fun listAllUsers(): PagingSource<Int, UserEntity>
    fun listFavourites():LiveData<List<UserEntity>>
    fun deleteUser(user: GitUser)
    fun deleteAll()
    fun insertAll(users:List<UserEntity>)
    fun createRemoteKeys(keys:List<UserRemoteKey>)
    fun getKeyByUserId(userId:Long):UserRemoteKey
    fun clearKeys()
    fun clearFavourites()
    fun fetchUser(username:String):LiveData<UserEntity>
    fun fetchLastKey():UserRemoteKey
    fun deleteUserById(id:Long)
    fun fetchUser(id:Long):UserEntity

    //For testing
    fun fetchUsersTest():List<UserEntity>
    fun saveUserTest(userEntity: UserEntity)
}