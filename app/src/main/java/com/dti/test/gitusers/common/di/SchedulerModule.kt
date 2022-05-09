package com.dti.test.gitusers.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
class SchedulerModule {

    val executor = Executors.newFixedThreadPool(5)

    @Provides
    @IoScheduler
    fun provideIOScheduler():Scheduler{
        return Schedulers.from(executor)
    }

    @Provides
    @MainScheduler
    fun provideMainScheduler():Scheduler{
        return AndroidSchedulers.mainThread()
    }


}


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoScheduler

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainScheduler