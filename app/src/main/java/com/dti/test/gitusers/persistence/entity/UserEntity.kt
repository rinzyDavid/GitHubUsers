package com.dti.test.gitusers.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "git_users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0,
    val id:Long,
    @ColumnInfo(name = "user_name")
    val username:String,
    @ColumnInfo(name = "photo_url")
    val avatar:String,
    @ColumnInfo(name = "detail_url")
    val detailsUrl:String ,
    @ColumnInfo(name = "repo_url")
    val repository:String,
    @ColumnInfo(name = "full_name")
    val fullName:String,
    @ColumnInfo(name = "organization")
    val company:String,
    @ColumnInfo(name = "blog")
    val blog:String,
    @ColumnInfo(name = "country_location")
    val location:String,
    @ColumnInfo(name = "email_address")
    val email:String,
    @ColumnInfo(name = "bio_description")
    val bioDescription:String,
    @ColumnInfo(name = "twitter_username")
    val twitterId:String,
    @ColumnInfo(name = "public_repo_count")
    val publicRepo:Int,
    @ColumnInfo(name = "public_gist_count")
    val publicGist:Int,
    @ColumnInfo(name = "followers")
    val followers:Int,
    @ColumnInfo(name = "following")
    val following:Int,
    @ColumnInfo(name = "is_favourite")
    var isFavourite:Boolean  = false,
    val total:Int


)
