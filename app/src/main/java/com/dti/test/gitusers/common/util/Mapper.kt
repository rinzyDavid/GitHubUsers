package com.dti.test.gitusers.common.util

/**
 * A generic data mapper which will help mapping data
 */
interface Mapper<I,O> {
    fun map(input:I):O
}