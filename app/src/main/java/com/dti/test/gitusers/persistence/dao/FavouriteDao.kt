package com.dti.test.gitusers.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dti.test.gitusers.persistence.entity.FavouriteEntity

@Dao
interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fav:FavouriteEntity)

    @Delete
    fun delete(fav: FavouriteEntity)

    @Query("delete from favourite_users")
    fun clearFavourites()

    @Query("select * from favourite_users")
    fun fetchAllUsers():LiveData<List<FavouriteEntity>>
}