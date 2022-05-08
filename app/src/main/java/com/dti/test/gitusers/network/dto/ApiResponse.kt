package com.dti.test.gitusers.network.dto

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("total_count")
    val total:Int,
    @SerializedName("incomplete_results")
    val isResultComplete:Boolean,
    @SerializedName("items")
    val data:List<UserDto>

)