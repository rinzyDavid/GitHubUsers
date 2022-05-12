package com.dti.test.gitusers.model.domain

data class GitUser(
    var userId:Long? = 0,
    var id:Long? = 0,
    var username:String? =  "",
    var avatar:String? = "",
    var detailsUrl:String? = "" ,
    var repository:String? ="",
    var fullName:String? = "",
    var company:String? = "",
    var blog:String? = "",
    var location:String? = "",
    var email:String? = "",
    var bioDescription:String? = "",
    var twitterId:String? = "",
    var publicRepo:Int? = 0,
    var publicGist:Int? = 0,
    var followers:Int? = 0,
    var following:Int? = 0,
    var isFavourite:Boolean? = false,
    var isDataComplete:Boolean? = false
){

}
