package com.dti.test.gitusers.network.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id:Long,
    @SerializedName("login")
    val username:String,
    @SerializedName("avatar")
    val photoUrl:String,
    @SerializedName("url")
    val detailsUrl:String ,
    @SerializedName("repos_url")
    val repository:String,
    @SerializedName("name")
    val fullName:String,
    @SerializedName("company")
    val company:String,
    @SerializedName("blog")
    val blog:String,
    @SerializedName("location")
    val location:String,
    @SerializedName("email")
    val email:String,
    @SerializedName("bio")
    val bioDescription:String,
    @SerializedName("twitter_username")
    val twitterId:String,
    @SerializedName("public_repos")
    val publicRepo:Int,
    @SerializedName("public_gists")
    val publicGist:Int,
    @SerializedName("followers")
    val followers:Int,
    @SerializedName("following")
    val following:Int
)
