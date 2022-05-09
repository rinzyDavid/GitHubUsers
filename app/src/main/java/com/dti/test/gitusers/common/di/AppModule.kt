package com.dti.test.gitusers.common.di

import com.dti.test.gitusers.model.service.IGitService
import com.dti.test.gitusers.persistence.repository.LocalUserRepository
import com.dti.test.gitusers.model.service.IGitServiceImpl
import com.dti.test.gitusers.persistence.repository.LocalUserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {


    @Binds
    abstract fun provideLocalRepository(repositoryImpl: LocalUserRepositoryImpl): LocalUserRepository

    @Binds
    abstract fun provideIGitService(iGitServiceImpl: IGitServiceImpl): IGitService
}