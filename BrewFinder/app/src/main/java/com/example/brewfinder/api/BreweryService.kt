package com.example.brewfinder.api

import android.util.Log
import com.example.brewfinder.data.Brewery
import com.example.brewfinder.data.BrewerySearchResults
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface BreweryService {
    @GET("breweries")
    suspend fun searchBreweries(
        @Query("by_city") query: String = "corvallis"
    ) : List<Brewery>

    companion object {

        private const val BASE_URL = "https://api.openbrewerydb.org/"
        fun create() : BreweryService {
            val moshi = Moshi.Builder()
                .addLast(KotlinJsonAdapterFactory())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
            Log.d("BreweryService", retrofit.toString())
            return retrofit.create(BreweryService::class.java)
        }
    }
}