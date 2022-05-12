package com.dti.test.gitusers.common.util

import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.network.dto.UserDto
import com.dti.test.gitusers.persistence.dao.UserDao
import com.dti.test.gitusers.persistence.entity.FavouriteEntity
import com.dti.test.gitusers.persistence.entity.UserEntity

/**
 * A generic data mapper which will help mapping data
 */
interface Mapper<I,O> {
    fun map(input:I):O
}

class EntityToModelMapper(

):Mapper<UserEntity,GitUser>{
    override fun map(input: UserEntity): GitUser {

        return GitUser(
            input.userId,
             input.id,
             input.username,
             input.avatar,
             input.detailsUrl ,
             input.repository,
             input.fullName,
             input.company,
             input.blog,
             input.location,
             input.email,
             input.bioDescription,
             input.twitterId,
             input.publicRepo,
             input.publicGist,
             input.followers,
             input.following,
             input.isFavourite,
            input.isDataComplete
        )
    }

}

class ModelToEntityMapperImpl:Mapper<GitUser,UserEntity>{
    override fun map(input: GitUser): UserEntity {

        return UserEntity(
            input.userId!!,
            input.id,
            input.username,
            input.avatar,
            input.detailsUrl ,
            input.repository,
            input.fullName,
            input.company,
            input.blog,
            input.location,
            input.email,
            input.bioDescription,
            input.twitterId,
            input.publicRepo,
            input.publicGist,
            input.followers,
            input.following,
            input.isFavourite,
            0,
             input.isDataComplete
        )
    }

}

class DtoToEntityMapper:Mapper<UserDto,UserEntity>{

    override fun map(input: UserDto): UserEntity {
        return UserEntity(
            0,
            input.id,
            input.username,
            input.photoUrl,
            input.detailsUrl ,
            input.repository,
            input.fullName,
            input.company,
            input.blog,
            input.location,
            input.email,
            input.bioDescription,
            input.twitterId,
            input.publicRepo,
            input.publicGist,
            input.followers,
            input.following,
            false,
            0,
            false
        )
    }

}


class FavModelToEntityMapperImpl:Mapper<GitUser,FavouriteEntity>{
    override fun map(input: GitUser): FavouriteEntity {

        return FavouriteEntity(
            input.userId!!,
            input.id,
            input.username,
            input.avatar,
            input.detailsUrl ,
            input.repository,
            input.fullName,
            input.company,
            input.blog,
            input.location,
            input.email,
            input.bioDescription,
            input.twitterId,
            input.publicRepo,
            input.publicGist,
            input.followers,
            input.following,
            input.isFavourite,

        )
    }

}

class FavEntityToModelMapper(

):ListMapper<FavouriteEntity,GitUser>{

    override fun map(input: List<FavouriteEntity>): List<GitUser> {
        return input.map {
            GitUser(
                it.userId,
                it.id,
                it.username,
                it.avatar,
                it.detailsUrl ,
                it.repository,
                it.fullName,
                it.company,
                it.blog,
                it.location,
                it.email,
                it.bioDescription,
                it.twitterId,
                it.publicRepo,
                it.publicGist,
                it.followers,
                it.following,
                it.isFavourite,
            )
        }
    }

}


