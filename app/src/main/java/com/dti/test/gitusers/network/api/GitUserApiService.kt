package com.dti.test.gitusers.network.api

import com.dti.test.gitusers.network.dto.ApiResponse
import com.dti.test.gitusers.network.dto.UserDto
import io.reactivex.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface GitUserApiService {

    @GET("search/users")
    fun fetchGitUsers(@Query("q")location:String,@Query("page")page:Int): Single<ApiResponse>
    @GET("users/{username}")
    fun fetchUserDetails(@Path("username")username:String):Single<UserDto>
}