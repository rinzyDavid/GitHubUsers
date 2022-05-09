package com.dti.test.gitusers.common.util

import androidx.paging.PagingData
import androidx.paging.map
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.persistence.entity.UserEntity

interface PageableMapper<I : Any,O : Any>: Mapper<PagingData<I>,PagingData<O>>

class PageableMapperImpl(
):PageableMapper<UserEntity,GitUser>{
    override fun map(input: PagingData<UserEntity>): PagingData<GitUser> {

       return input.map {it->
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