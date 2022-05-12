package com.dti.test.gitusers.persistence.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dti.test.gitusers.persistence.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user:UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users:List<UserEntity>)

    @Update
    fun update(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)

    @Query("delete from git_users where userId = :userId")
    fun deleteUserById(userId:Long)

    @Query("select * from git_users where id = :id")
    fun findById(id:Long):UserEntity

    @Query("select * from git_users order by userId")
    fun listUsers():PagingSource<Int,UserEntity>

    @Query("select * from git_users where is_favourite = :IsFavourite")
    fun listFavourites(IsFavourite:Boolean):LiveData<List<UserEntity>>

    @Query("delete from git_users")
    fun deleteAllUsers()

    @Query("update git_users set is_favourite = :value")
    fun clearFavourites(value:Boolean)

    @Query("select * from git_users where user_name = :username")
    fun fetchUserByUsername(username:String):LiveData<UserEntity>

    //For testing
    @Query("select * from git_users order by userId")
    fun fetchUsersTest():List<UserEntity>
}