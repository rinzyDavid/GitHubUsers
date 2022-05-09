package com.dti.test.gitusers.common.di

import android.content.Context
import com.dti.test.gitusers.R
import com.dti.test.gitusers.network.api.GitUserApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun baseUrl(@ApplicationContext context: Context): String {
        return context.resources.getString(R.string.user_api)
    }

    @Provides
    @Singleton
    fun provideOkHttpCache(@ApplicationContext application: Context): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }



    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation() //.setDateFormat("dd-MM-yyyy 'at' HH:mm")
            .setLenient()

        // gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache?): OkHttpClient {
        val client = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client.addInterceptor(loggingInterceptor)
        client.cache(cache)
        client.callTimeout(60, TimeUnit.SECONDS)

        return client.build()
    }

    @Provides
    @Singleton
    fun provideNetworkApi(
        @ApplicationContext context: Context,
        gson: Gson?,
        okHttpClient: OkHttpClient?
    ): GitUserApiService {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create()) //important
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .baseUrl(baseUrl(context))
            .client(okHttpClient!!)
            .build()
        return retrofit.create(GitUserApiService::class.java)
    }

}