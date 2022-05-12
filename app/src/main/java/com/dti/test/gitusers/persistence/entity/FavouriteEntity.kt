package com.dti.test.gitusers.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_users")
data class FavouriteEntity(
    @PrimaryKey
    var userId: Long = 0 ,
    var id:Long? = 0,
    @ColumnInfo(name = "user_name")
    var username:String? = "",
    @ColumnInfo(name = "photo_url")
    var avatar:String? ="",
    @ColumnInfo(name = "detail_url")
    var detailsUrl:String?="" ,
    @ColumnInfo(name = "repo_url")
    var repository:String? ="",
    @ColumnInfo(name = "full_name")
    var fullName:String? = "",
    @ColumnInfo(name = "organization")
    var company:String? ="",
    @ColumnInfo(name = "blog")
    var blog:String? ="",
    @ColumnInfo(name = "country_location")
    var location:String? = "",
    @ColumnInfo(name = "email_address")
    var email:String? = "",
    @ColumnInfo(name = "bio_description")
    var bioDescription:String? ="",
    @ColumnInfo(name = "twitter_username")
    var twitterId:String? = "",
    @ColumnInfo(name = "public_repo_count")
    var publicRepo:Int? = 0,
    @ColumnInfo(name = "public_gist_count")
    var publicGist:Int? = 0,
    @ColumnInfo(name = "followers")
    var followers:Int? = 0,
    @ColumnInfo(name = "following")
    var following:Int? = 0,
    @ColumnInfo(name = "is_favourite")
    var isFavourite:Boolean?  = false,
) {
}