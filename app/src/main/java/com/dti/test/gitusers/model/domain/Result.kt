package com.dti.test.gitusers.model.domain

sealed class Result<T>{
    data class Success<T>(val value: T) : Result<T>()
    data class Failure<T>(val throwable: Throwable) : Result<T>()
}
