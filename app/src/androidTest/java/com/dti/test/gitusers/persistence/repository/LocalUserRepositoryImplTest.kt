package com.dti.test.gitusers.persistence.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dti.test.gitusers.common.util.*
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.persistence.db.UsersRoomDb
import com.dti.test.gitusers.persistence.entity.UserEntity
import com.dti.test.gitusers.persistence.mapper.UserDataMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.io.IOException

class LocalUserRepositoryImplTest {

    private lateinit var userRepository: UserRepository
    private lateinit var mapper: UserDataMapper
    private lateinit var db:UsersRoomDb


    @Before
    fun setUp() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, UsersRoomDb::class.java
        ).build()
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
        userRepository = LocalUserRepositoryImpl(
            userDao = db.userDao(),
            keysDao = db.remoteKeyDao(),
            mapper

        )
    }

    @Test
    fun db_save_user_returns_true() = runBlocking {

        val gitUser = UserEntity(1,2,"james test")
        userRepository.saveUserTest(gitUser)
        val users = userRepository.fetchUsersTest()
        assertTrue(users.isNotEmpty())
    }

    @Test
    fun repo_save_user_returns_true() = runBlocking {

        val gitUser = GitUser(1,2,"james test")
        userRepository.saveUser(gitUser)
        val users = userRepository.fetchUsersTest()
        assertTrue(users.isNotEmpty())


    }

    @Test
    fun repo_delete_All_user_returns_true()= runBlocking {
        val gitUser = GitUser(1,2,"james test")
        userRepository.saveUser(gitUser)
        userRepository.deleteAll()
        val users = userRepository.fetchUsersTest()
        assertTrue(users.isEmpty())
    }

    @Test
    fun repo_delete_user_returns_true()= runBlocking {

    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}