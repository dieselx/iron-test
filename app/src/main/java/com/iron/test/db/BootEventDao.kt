package com.iron.test.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class BootEventDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addEvent(event: BootEvent)

    @Query("select * from events")
    abstract suspend fun getEvents(): List<BootEvent>
}