package com.example.expensetrackerapp.room_database

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class Details(

    val date: String,
    val category: String,
    val desc:String,
    val amount:String,
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
)
