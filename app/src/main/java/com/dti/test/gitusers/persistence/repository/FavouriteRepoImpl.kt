package com.dti.test.gitusers.persistence.repository

import androidx.lifecycle.LiveData
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.persistence.dao.FavouriteDao
import com.dti.test.gitusers.persistence.entity.FavouriteEntity
import javax.inject.Inject

class FavouriteRepoImpl @Inject constructor(
   val favouriteDao: FavouriteDao
) :FavouriteRepo {
    override fun fetchFavourites(): LiveData<List<FavouriteEntity>> {

        return favouriteDao.fetchAllUsers()
    }

    override fun createFavourite(user: FavouriteEntity) {
       favouriteDao.insert(user)
    }

    override fun removeFavourite(user: FavouriteEntity) {
        favouriteDao.delete(user)
    }

    override fun removeAllFavourites() {
        favouriteDao.clearFavourites()
    }
}