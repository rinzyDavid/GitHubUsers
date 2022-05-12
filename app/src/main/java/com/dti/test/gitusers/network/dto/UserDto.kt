package com.dti.test.gitusers.network.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id:Long? = 0,
    @SerializedName("login")
    val username:String? = "",
    @SerializedName("avatar_url")
    val photoUrl:String? ="",
    @SerializedName("url")
    val detailsUrl:String? = "" ,
    @SerializedName("repos_url")
    val repository:String? = "",
    @SerializedName("name")
    val fullName:String?= "",
    @SerializedName("company")
    val company:String?="",
    @SerializedName("blog")
    val blog:String? = "",
    @SerializedName("location")
    val location:String? = "",
    @SerializedName("events_url")
    val email:String? = "",
    @SerializedName("bio")
    val bioDescription:String? = "",
    @SerializedName("twitter_username")
    val twitterId:String? = "",
    @SerializedName("public_repos")
    val publicRepo:Int? = 0,
    @SerializedName("public_gists")
    val publicGist:Int? = 0,
    @SerializedName("followers")
    val followers:Int? = 0,
    @SerializedName("following")
    val following:Int? = 0,
    val total:Int? = 0
)
