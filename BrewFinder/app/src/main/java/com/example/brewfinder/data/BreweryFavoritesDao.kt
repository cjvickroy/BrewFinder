package com.example.brewfinder.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BreweryFavoritesDao {
    @Insert
    suspend fun insert(brewery: Brewery)

    @Delete
    suspend fun delete(brewery: Brewery)

    @Query("SELECT * FROM Brewery")
    fun getAllBreweries(): Flow<List<Brewery>>

    @Query("SELECT * FROM Brewery WHERE name = :name LIMIT 1")
    fun getBreweryByName(name: String): Flow<Brewery?>



}