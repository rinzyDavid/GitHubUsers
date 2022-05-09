package com.dti.test.gitusers.views.users.vm

import androidx.lifecycle.ViewModel
import com.dti.test.gitusers.model.service.IGitService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserListVm @Inject constructor(
    val iGitService: IGitService
):ViewModel() {


}