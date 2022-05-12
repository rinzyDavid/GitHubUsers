package com.dti.test.gitusers.common.di

import android.content.Context
import com.dti.test.gitusers.common.util.*
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.persistence.dao.FavouriteDao

import com.dti.test.gitusers.persistence.dao.RemoteKeyDao
import com.dti.test.gitusers.persistence.dao.UserDao
import com.dti.test.gitusers.persistence.db.UsersRoomDb
import com.dti.test.gitusers.persistence.entity.UserEntity
import com.dti.test.gitusers.persistence.mapper.UserDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * This module helps to create, inject database and dao instances
 */

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): UsersRoomDb {
        return UsersRoomDb.getInstance(context)
    }

    @Provides
    fun provideUserDao(appDatabase: UsersRoomDb): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    fun provideKeysDao(appDatabase: UsersRoomDb):RemoteKeyDao{
        return appDatabase.remoteKeyDao()
    }

    @Provides
    fun provideFavouriteDao(appDatabase: UsersRoomDb):FavouriteDao{
        return appDatabase.favouriteDao()
    }

    @Provides
    fun provideDataMapper():UserDataMapper{
        return UserDataMapper(
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


}