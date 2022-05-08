package com.dti.test.gitusers.model.domain

data class GitUser(
    val id:Long,
    val username:String,
    val avatar:String,
    val detailsUrl:String ,
    val repository:String,
    val fullName:String,
    val company:String,
    val blog:String,
    val location:String,
    val email:String,
    val bioDescription:String,
    val twitterId:String,
    val publicRepo:Int,
    val publicGist:Int,
    val followers:Int,
    val following:Int,
    var isFavourite:Boolean
)
