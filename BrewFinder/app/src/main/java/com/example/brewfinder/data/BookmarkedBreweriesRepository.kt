package com.example.brewfinder.data

class BookmarkedBreweriesRepository(private val dao: BreweryFavoritesDao) {
    suspend fun insertBookmarkedBrewery(brewery: Brewery) = dao.insert(brewery)
    suspend fun deleteBookmarkedBrewery(brewery: Brewery) = dao.delete(brewery)
    fun getAllBookmarkedBreweries() = dao.getAllBreweries()
    fun getBookmarkedBreweryByName(name: String)= dao.getBreweryByName(name)

}