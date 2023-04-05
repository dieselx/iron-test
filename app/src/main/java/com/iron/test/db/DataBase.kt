package com.iron.test.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BootEvent::class], version = 1, exportSchema = true)
abstract class DataBase: RoomDatabase() {
    abstract fun bootEventDao(): BootEventDao
}