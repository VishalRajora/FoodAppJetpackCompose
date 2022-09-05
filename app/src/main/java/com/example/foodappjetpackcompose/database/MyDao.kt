package com.example.foodappjetpackcompose.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.foodappjetpackcompose.data.AuthModelClass

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertAuth(auth: AuthModelClass): Long
}
