package com.example.foodappjetpackcompose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodappjetpackcompose.data.AuthModelClass

@Database(entities = [AuthModelClass::class], version = 1, exportSchema = true)
abstract class AuthDatabase : RoomDatabase() {
    abstract fun authDao(): MyDao
}