package com.dti.test.gitusers.model.service

import androidx.lifecycle.LiveData
import com.dti.test.gitusers.model.domain.GitUser
import com.dti.test.gitusers.model.repository.LocalUserRepository
import javax.inject.Inject
import com.dti.test.gitusers.model.domain.Result
import com.dti.test.gitusers.model.repository.IGitService
import com.dti.test.gitusers.network.api.GitUserApiService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.internal.schedulers.IoScheduler


/**

 */
class IGitServiceImpl @Inject constructor(
    localRepo:LocalUserRepository,
    networkRepo:GitUserApiService,
    ioScheduler:  IoScheduler,
    mainScheduler:AndroidSchedulers
): IGitService {


    override fun fetchUsers(page: Int): LiveData<Result<List<GitUser>>> {
        TODO("Not yet implemented")
    }

    override fun fetchFavourite(page: Int): LiveData<Result<List<GitUser>>> {
        TODO("Not yet implemented")
    }

    override fun updateUser(user: GitUser, isFavourite: Boolean) {
        TODO("Not yet implemented")
    }

    override fun removeFromFavourite(user: GitUser) {
        TODO("Not yet implemented")
    }

    override fun removeAllFromFavourite() {
        TODO("Not yet implemented")
    }
}