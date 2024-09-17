package com.example.assignment6.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDatabaseDao {

    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM users order by userId desc limit 1")
    fun getUser(): User?

    @Query("DELETE FROM users")
    fun clear()


}