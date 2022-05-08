package com.dti.test.gitusers.persistence.dao

import androidx.lifecycle.LiveData
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

    @Update
    fun update(user: UserEntity)

    @Delete
    fun deleteUser(user: UserEntity)

    @Query("select * from git_users")
    fun listUsers():List<UserEntity>

    @Query("select * from git_users where is_favourite = :IsFavourite")
    fun listFavourites(IsFavourite:Boolean):List<UserEntity>
}