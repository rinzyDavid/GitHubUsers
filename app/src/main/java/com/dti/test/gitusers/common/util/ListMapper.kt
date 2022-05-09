package com.dti.test.gitusers.common.util

import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.network.dto.UserDto
import com.dti.test.gitusers.persistence.entity.UserEntity

interface ListMapper<I,O>:Mapper<List<I>,List<O>>

class ListMapperImpl<I, O>(
    private val mapper: Mapper<I, O>
) : ListMapper<I, O> {
    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }
}

class EntityListToModelMapper:ListMapper<UserEntity,GitUser>{
    override fun map(input: List<UserEntity>): List<GitUser> {

        return input.map {
            GitUser(
                it.userId,
                it.id,
                it.username,
                it.avatar,
                it.detailsUrl,
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
                it.isFavourite
            )
        }
    }

}

class DtoListToEntityMapper:ListMapper<UserDto,UserEntity>{
    override fun map(input: List<UserDto>): List<UserEntity> {
        return input.map {
            UserEntity(
                0,
                it.id,
                it.username,
                it.photoUrl,
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
                false,
                0
            )
        }
    }

}
