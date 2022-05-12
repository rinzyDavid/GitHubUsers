package com.dti.test.gitusers.persistence.repository

import androidx.lifecycle.LiveData
import com.dti.test.gitusers.persistence.entity.FavouriteEntity

/**
 * A Favourite repository contract for accessing FavouriteUser objects
 * from local Room database
 */
interface FavouriteRepo {

    fun fetchFavourites():LiveData<List<FavouriteEntity>>
    fun createFavourite(user:FavouriteEntity)
    fun removeFavourite(user:FavouriteEntity)
    fun removeAllFavourites()
}