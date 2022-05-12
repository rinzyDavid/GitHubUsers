package com.dti.test.gitusers.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dti.test.gitusers.persistence.entity.UserRemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(keys:List<UserRemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE userId = :id")
    fun remoteKeysByUserId(id:Long):UserRemoteKey

    @Query("select * from remote_keys ")
    fun fetchAllKeys():List<UserRemoteKey>

    @Query("delete from remote_keys")
    fun clearRemoteKeys()

}