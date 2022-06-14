package com.example.brewfinder.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.githubsearchwithroom.data.LoadingStatus
import com.example.brewfinder.api.BreweryService
import com.example.brewfinder.data.BreweriesRepository
import com.example.brewfinder.data.Brewery
import kotlinx.coroutines.launch

class BrewerySearchViewModel : ViewModel() {
    private val repository = BreweriesRepository(BreweryService.create())

    private val _searchResults = MutableLiveData<List<Brewery>?>(null)
    val searchResults: LiveData<List<Brewery>?> = _searchResults

    private val _loadingStatus = MutableLiveData(LoadingStatus.SUCCESS)
    val loadingStatus: LiveData<LoadingStatus> = _loadingStatus

    fun loadSearchResults(query: String) {
        viewModelScope.launch {
            _loadingStatus.value = LoadingStatus.LOADING
            val result = repository.loadBreweriesSearch(query)
            _searchResults.value = result.getOrNull()
            _loadingStatus.value = when (result.isSuccess) {
                true -> LoadingStatus.SUCCESS
                false -> LoadingStatus.ERROR
            }
        }
    }
}