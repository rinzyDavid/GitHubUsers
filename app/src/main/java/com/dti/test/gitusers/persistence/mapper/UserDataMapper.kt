package com.dti.test.gitusers.persistence.mapper

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.dti.test.gitusers.common.util.EntityToModelMapper
import com.dti.test.gitusers.common.util.FavEntityToModelMapper
import com.dti.test.gitusers.common.util.FavModelToEntityMapperImpl
import com.dti.test.gitusers.common.util.Mapper
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.network.dto.UserDto
import com.dti.test.gitusers.persistence.entity.FavouriteEntity
import com.dti.test.gitusers.persistence.entity.UserEntity
import javax.inject.Inject

/**
 * A Facade object to help in injecting various object mapper
 */
class UserDataMapper @Inject constructor(
     val modelToEntityMapper: Mapper<GitUser, UserEntity>,
     val entityLvDataToModelMapper: Mapper<PagingData<UserEntity>,PagingData<GitUser>>,
     val entityListToModelMapper: Mapper<List<UserEntity>,List<GitUser>>,
     val dtoToEntityList: Mapper<List<UserDto>,List<UserEntity>>,
     val entityToModelMapper: Mapper<UserEntity,GitUser>,
     val dtoToEntityMapper:Mapper<UserDto,UserEntity>,
     val favEntityToModelMapper: Mapper<List<FavouriteEntity>,List<GitUser>>,
     val favModelToEntityMapper: Mapper<GitUser,FavouriteEntity>

)