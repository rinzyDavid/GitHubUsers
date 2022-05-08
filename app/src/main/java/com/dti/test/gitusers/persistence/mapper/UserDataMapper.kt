package com.dti.test.gitusers.persistence.mapper

import com.dti.test.gitusers.common.util.Mapper
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.persistence.entity.UserEntity

class UserDataMapper(
     val entityToModelMapper: Mapper<UserEntity, GitUser>,
     val modelToEntityMapper: Mapper<GitUser, UserEntity>,
     val entityListToModelMapper: Mapper<List<UserEntity>,List<GitUser>>,
     val modelListToEntityMapper: Mapper<List<GitUser>,List<UserEntity>>

)