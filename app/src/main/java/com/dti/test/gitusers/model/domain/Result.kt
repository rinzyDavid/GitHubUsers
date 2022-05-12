package com.dti.test.gitusers.model.domain



sealed class ResultData<out T, out E> {

    data class Success<out T>(val value: T) : ResultData<T, Nothing>()

    data class Failure<out E>(val error: E) : ResultData<Nothing, E>()

    inline fun <C> fold(success: (T) -> C, failure: (E) -> C): C = when (this) {
        is Success -> success(value)
        is Failure -> failure(error)
    }

}

typealias SimpleResult<T> = ResultData<T, Throwable>
