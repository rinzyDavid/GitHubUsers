package com.dti.test.gitusers.persistence.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.persistence.dao.RemoteKeyDao
import com.dti.test.gitusers.persistence.dao.UserDao
import com.dti.test.gitusers.persistence.entity.UserEntity
import com.dti.test.gitusers.persistence.entity.UserRemoteKey
import com.dti.test.gitusers.persistence.mapper.UserDataMapper
import javax.inject.Inject

/**
 * This Repository class provides implementation for accessing local room database
 * for UserEntity and UserRemoteKey
 */
class LocalUserRepositoryImpl @Inject constructor(
     val userDao: UserDao,
     val keysDao: RemoteKeyDao,
    val userDataMapper: UserDataMapper
): UserRepository {
    override fun saveUser(user: GitUser) {
        val userEntity = userDataMapper.modelToEntityMapper.map(user)
        userDao.insert(userEntity)
    }

    override fun updateUser(user: GitUser) {
        val userEntity = userDataMapper.modelToEntityMapper.map(user)
        userDao.update(userEntity)
    }

    override fun updateUser(user: UserEntity) {

        userDao.update(user)
    }

    override fun listAllUsers(): PagingSource<Int, UserEntity> {
        return userDao.listUsers()
    }

    override fun listFavourites(): LiveData<List<UserEntity>> {
       return userDao.listFavourites(true)
    }

    override fun deleteUser(user: GitUser) {
        userDao.deleteUser(userDataMapper.modelToEntityMapper.map(user))
    }

    override fun deleteAll() {
        userDao.deleteAllUsers()
    }

    override fun insertAll(users: List<UserEntity>) {
        userDao.insertAll(users)
    }

    override fun createRemoteKeys(keys: List<UserRemoteKey>) {
        keysDao.insert(keys)
    }

    override fun getKeyByUserId(userId: Long): UserRemoteKey {
        return keysDao.remoteKeysByUserId(userId)
    }

    override fun clearKeys() {
        keysDao.clearRemoteKeys()
    }

    override fun clearFavourites() {
       userDao.clearFavourites(true)
    }

    override fun fetchUser(username: String): LiveData<UserEntity> {
        return userDao.fetchUserByUsername(username)
    }

    override fun fetchUser(id: Long): UserEntity {
        return userDao.findById(id)
    }

    override fun fetchLastKey():UserRemoteKey {
        return keysDao.fetchAllKeys().last()
    }

    override fun deleteUserById(id: Long) {
       userDao.deleteUserById(id)
    }

    override fun fetchUsersTest(): List<UserEntity> {
        return userDao.fetchUsersTest()
    }

    override fun saveUserTest(userEntity: UserEntity) {
        userDao.insert(userEntity)
    }
}