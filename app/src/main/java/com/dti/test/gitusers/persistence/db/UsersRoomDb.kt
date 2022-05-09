package com.dti.test.gitusers.persistence.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dti.test.gitusers.persistence.dao.RemoteKeyDao
import com.dti.test.gitusers.persistence.dao.UserDao
import com.dti.test.gitusers.persistence.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UsersRoomDb:RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun remoteKeyDao():RemoteKeyDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: UsersRoomDb? = null

        fun getInstance(context: Context): UsersRoomDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): UsersRoomDb {
            return Room.databaseBuilder(context, UsersRoomDb::class.java, "gistusers.db")
                .build()
        }


    }



}