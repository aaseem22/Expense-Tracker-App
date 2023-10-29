package com.example.expensetrackerapp.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailsDAO {

    @Upsert
   suspend fun insetDetails(details: Details)

    @Delete
    suspend fun deleteDetails(details: Details)

    @Query("SELECT * FROM details ORDER BY date ASC")
    fun getDetailsByDate(): Flow<List<Details>>

    @Query("SELECT * FROM details ORDER BY category ASC")
    fun getDetailsByCategory(): Flow<List<Details>>

    @Query("SELECT * FROM details ORDER BY amount ASC")
    fun getDetailsByAmount(): Flow<List<Details>>
}