package com.dti.test.gitusers.common.util

import com.dti.test.gitusers.model.domain.GitUser

interface AdapterListener {
    fun onClick(user:GitUser)
}