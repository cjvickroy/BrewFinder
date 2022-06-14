package com.example.brewfinder.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.githubsearchwithroom.data.LoadingStatus
import com.example.brewfinder.R
import com.example.brewfinder.data.Brewery
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar

var cleared = false

class MainActivity : AppCompatActivity() {

    private val breweryAdapter = BreweryListAdapter(::onBreweryClick)
    private lateinit var breweryListRV: RecyclerView

    private lateinit var searchBoxET: EditText
    private lateinit var searchResultsListRV: RecyclerView
    private lateinit var loadingIndicator: CircularProgressIndicator


    private val viewModel: BrewerySearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchBoxET = findViewById(R.id.et_search_box)

        breweryListRV = findViewById(R.id.rv_brewery_list)
        loadingIndicator = findViewById(R.id.loading_indicator)

        breweryListRV.layoutManager = LinearLayoutManager(this)
        breweryListRV.setHasFixedSize(true)

        breweryListRV.adapter = breweryAdapter

        viewModel.searchResults.observe(this) { searchResults ->
            breweryAdapter.updateBreweryList(searchResults)
        }

//        viewModel.loadingStatus.observe(this) { uiState ->
//            when (uiState) {
//                LoadingStatus.LOADING -> {
//                    loadingIndicator.visibility = View.VISIBLE
//                    searchResultsListRV.visibility = View.INVISIBLE
//                }
//                else -> {
//                    loadingIndicator.visibility = View.INVISIBLE
//                    searchResultsListRV.visibility = View.VISIBLE
//                }
//            }
//        }

        val searchBtn: Button = findViewById(R.id.btn_search)
        searchBtn.setOnClickListener {
            val query = searchBoxET.text.toString()
            if (!TextUtils.isEmpty(query)) {
                viewModel.loadSearchResults(query)
                breweryListRV.scrollToPosition(0)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        if (cleared == true) {
            Snackbar.make(
                findViewById(R.id.coordinator_layout),
                R.string.action_cleared_loc,
                Snackbar.LENGTH_LONG
            ).show()
            cleared = false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_bookmarks -> {
                val intent = Intent(this, BookmarkedBreweriesActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onBreweryClick(brewery: Brewery ){
        val intent = Intent(this, BreweryDetailActivity::class.java).apply{
            putExtra(EXTRA_BREWERY, brewery)
        }
        startActivity(intent)
    }

}