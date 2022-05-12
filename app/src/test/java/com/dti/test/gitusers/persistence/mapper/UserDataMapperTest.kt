package com.dti.test.gitusers.persistence.mapper

import com.dti.test.gitusers.common.util.*
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.persistence.entity.UserEntity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserDataMapperTest{


    private lateinit var mapper: UserDataMapper

    @Before
    fun setup(){
        mapper = UserDataMapper(
            ModelToEntityMapperImpl(),
            PageableMapperImpl(),
            EntityListToModelMapper(),
            DtoListToEntityMapper(),
            EntityToModelMapper(),
            DtoToEntityMapper(),
            FavEntityToModelMapper(),
            FavModelToEntityMapperImpl()
        )
    }

    @Test
    fun model_toEntity_Mapping_Resturns_Valid(){

        val userModel = setupModel()
        val expected = mapper.modelToEntityMapper.map(userModel)
        assertEquals(expected.userId,userModel.userId)
        assertEquals(expected.username,userModel.username)

    }




    private fun setupModel():GitUser{
        return GitUser(
            userId = 1,
            id = 2,
            username = "John Bull",
            avatar = "demo_url",
            "demodetails",
            repository = "demo_repository",
            fullName = "Demo Fulname",
            company = "Demo company",
             blog = "Demo blog",
            location = "Demo location",
            email = "demo email",
            bioDescription = "demo bio",
            twitterId= "demo twitter",
            publicRepo = 0,
            publicGist = 0,
            followers = 0,
            following = 0,
            isFavourite = false,
            isDataComplete = false
        )
    }

}