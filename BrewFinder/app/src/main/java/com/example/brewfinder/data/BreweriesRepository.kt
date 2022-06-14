package com.example.brewfinder.data

import android.app.DownloadManager
import android.text.TextUtils
import android.util.Log
import com.example.brewfinder.api.BreweryService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BreweriesRepository (
    private val service: BreweryService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun loadBreweriesSearch(
        query: String
    ): Result<List<Brewery>> =
        withContext(ioDispatcher) {
            try {
                val results = service.searchBreweries(
                    buildBreweryQuery(query) //This is pointless
                    //Going to come back and fix this
                )
                Result.success(results)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    private fun buildBreweryQuery(query: String) : String {
        return query
    }

}