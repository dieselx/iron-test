package com.iron.test.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class BootEvent(
    @PrimaryKey val time: Long
)