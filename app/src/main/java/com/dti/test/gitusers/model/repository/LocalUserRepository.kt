package com.dti.test.gitusers.model.repository

import androidx.lifecycle.LiveData
import com.dti.test.gitusers.model.domain.GitUser

interface LocalUserRepository {
    fun saveUser(user:GitUser)
    fun updateUser(user: GitUser)
    fun listAllUsers():List<GitUser>
    fun listFavourites():List<GitUser>
    fun deleteUser(user: GitUser)
}