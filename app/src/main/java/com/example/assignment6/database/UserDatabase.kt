package com.example.assignment6.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDatabaseDao() : UserDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java,
                        "users"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}

