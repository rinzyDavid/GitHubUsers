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

class LocalUserRepositoryImpl @Inject constructor(
     val userDao: UserDao,
     val keysDao: RemoteKeyDao,
    val userDataMapper: UserDataMapper
): LocalUserRepository {
    override fun saveUser(user: GitUser) {

        val userEntity = userDataMapper.modelToEntityMapper.map(user)
        userDao.insert(userEntity)
    }

    override fun updateUser(user: GitUser) {
        val userEntity = userDataMapper.modelToEntityMapper.map(user)
        userDao.update(userEntity)
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
}