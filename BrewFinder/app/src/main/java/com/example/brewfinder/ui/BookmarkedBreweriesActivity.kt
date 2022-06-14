package com.example.brewfinder.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.brewfinder.R
import com.example.brewfinder.data.Brewery

class BookmarkedBreweriesActivity: AppCompatActivity() {
    private val breweryListAdapter = BreweryListAdapter(::onBreweryClick)
    private lateinit var bookmarkedRV: RecyclerView

    private val viewModel: BookmarkedBreweryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmarked_breweries)

        bookmarkedRV = findViewById(R.id.rv_bookmarked_breweries)
        bookmarkedRV.layoutManager = LinearLayoutManager(this)
        bookmarkedRV.setHasFixedSize(true)
        bookmarkedRV.adapter = this.breweryListAdapter

        viewModel.bookmarkedBreweries.observe(this) { bookmarkedBreweries ->
            breweryListAdapter.updateBreweryList(bookmarkedBreweries)
        }
    }

    private fun onBreweryClick(brewery: Brewery) {
        val intent = Intent(this, BreweryDetailActivity::class.java).apply {
            putExtra(EXTRA_BREWERY, brewery)
        }
        startActivity(intent)
    }
}