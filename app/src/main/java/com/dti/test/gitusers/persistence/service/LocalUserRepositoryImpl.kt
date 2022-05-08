package com.dti.test.gitusers.persistence.service

import com.dti.test.gitusers.common.util.Mapper
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.model.repository.LocalUserRepository
import com.dti.test.gitusers.persistence.dao.UserDao
import com.dti.test.gitusers.persistence.entity.UserEntity
import com.dti.test.gitusers.persistence.mapper.UserDataMapper
import javax.inject.Inject

class LocalUserRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
   private val userDataMapper: UserDataMapper
):LocalUserRepository {
    override fun saveUser(user: GitUser) {

        val userEntity = userDataMapper.modelToEntityMapper.map(user)
        userDao.insert(userEntity)
    }

    override fun updateUser(user: GitUser) {
        val userEntity = userDataMapper.modelToEntityMapper.map(user)
        userDao.update(userEntity)
    }

    override fun listAllUsers(): List<GitUser> {

        val users = userDao.listUsers()
        return userDataMapper.entityListToModelMapper.map(users)
    }

    override fun listFavourites(): List<GitUser> {
       return userDataMapper.entityListToModelMapper.map(userDao.listFavourites(true))
    }

    override fun deleteUser(user: GitUser) {
        userDao.deleteUser(userDataMapper.modelToEntityMapper.map(user))
    }
}