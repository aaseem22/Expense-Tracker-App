package com.example.expensetrackerapp.room_database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Details::class],
    version = 1
)
abstract class DetailDatabase:RoomDatabase() {
    abstract val dao: DetailsDAO
}