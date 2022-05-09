package com.dti.test.gitusers.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class UserRemoteKey(
    @PrimaryKey val userId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)
