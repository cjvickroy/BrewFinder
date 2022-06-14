package com.example.brewfinder.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.brewfinder.data.AppDatabase
import com.example.brewfinder.data.BookmarkedBreweriesRepository
import com.example.brewfinder.data.Brewery
import kotlinx.coroutines.launch

class BookmarkedBreweryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = BookmarkedBreweriesRepository(
        AppDatabase.getInstance(application).breweryDao()
    )

    val bookmarkedBreweries = repository.getAllBookmarkedBreweries().asLiveData()

    fun addBookmarkedBrewery(brewery: Brewery) {
        viewModelScope.launch {
            repository.insertBookmarkedBrewery(brewery)
        }
    }

    fun removeBookmarkedBrewery(brewery: Brewery) {
        viewModelScope.launch {
            repository.deleteBookmarkedBrewery(brewery)
        }
    }

    fun getBookmarkedBreweryByName(name: String) =
        repository.getBookmarkedBreweryByName(name).asLiveData()
}