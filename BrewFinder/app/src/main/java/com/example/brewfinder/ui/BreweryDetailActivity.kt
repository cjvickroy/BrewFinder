package com.example.brewfinder.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.GestureDetector
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.example.brewfinder.R
import com.example.brewfinder.data.Brewery
import kotlin.math.abs


const val EXTRA_BREWERY = "Brewery"

class BreweryDetailActivity : AppCompatActivity(), GestureDetector.OnGestureListener {
    private var brewery: Brewery? = null
    private var isBookmarked = false

    // Declaring gesture detector, swipe threshold, and swipe velocity threshold
    private lateinit var gestureDetector: GestureDetector
    private val swipeThreshold = 100
    private val swipeVelocityThreshold = 100

    private val viewModel: BookmarkedBreweryViewModel by viewModels()


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brewery_detail)
        gestureDetector = GestureDetector(this)

        if (intent != null && intent.hasExtra(EXTRA_BREWERY)) {
            brewery = intent.getSerializableExtra(EXTRA_BREWERY) as Brewery

            findViewById<TextView>(R.id.tv_name).text = brewery!!.name
            findViewById<TextView>(R.id.tv_type).text = brewery!!.brewery_type.capitalize()
            findViewById<TextView>(R.id.tv_street_address).text = brewery!!.street

            val formattedCityState = "${brewery!!.city}, ${brewery!!.state}"
            findViewById<TextView>(R.id.tv_city_state).text = formattedCityState

            findViewById<TextView>(R.id.tv_website).text = brewery!!.website_url
            val formattedNumber: String? = PhoneNumberUtils.formatNumber(brewery!!.phone)
            findViewById<TextView>(R.id.tv_phone_number).text = formattedNumber
        }

    }

    // Override this method to recognize touch event
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (gestureDetector.onTouchEvent(event)) {
            true
        }
        else {
            super.onTouchEvent(event)
        }
    }

    override fun onDown(e: MotionEvent?): Boolean {
        Log.i("Swipe","onDown")
        return false
    }

    override fun onShowPress(e: MotionEvent?) {
        return
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
        Log.i("Swipe","onLongPress")
        val streetAddress = brewery!!.street
        val gmmIntentUri = Uri.parse("geo:0,0?q=${streetAddress}&z=3")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
        return
    }

    override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        try {
            val diffY = e2.y - e1.y
            val diffX = e2.x - e1.x
            if (abs(diffX) > abs(diffY)) {
                if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                    if (diffX > 0) {
                        //swipe to the life open activity
                        Log.i("Swipe","OnFling to Left")
                        cleared = true
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
        catch (exception: Exception) {
            Log.i("Swipe",exception.toString())
            exception.printStackTrace()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_brewery_detail, menu)
        val bookmarkItem = menu.findItem(R.id.action_bookmark)
        viewModel.getBookmarkedBreweryByName(brewery!!.name).observe(this) { bookmarkedBrewery ->
            when (bookmarkedBrewery) {
                null -> {
                    isBookmarked = false
                    bookmarkItem.icon = AppCompatResources.getDrawable(
                        this,
                        R.drawable.ic_sports_bar_black_24dp
                    )
                }
                else -> {
                    isBookmarked = true
                    bookmarkItem.icon = AppCompatResources.getDrawable(
                        this,
                        R.drawable.ic_sports_bar_black_24dp_1
                    )
                }
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_bookmark -> {
                toggleRepoBookmark(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method toggles the state of the bookmark icon in the top app bar whenever the user
     * clicks it.
     */
    private fun toggleRepoBookmark(menuItem: MenuItem) {
        if (brewery != null) {
            isBookmarked = !isBookmarked
            menuItem.isChecked = isBookmarked
            when (isBookmarked) {
                true -> {
                    viewModel.addBookmarkedBrewery(brewery!!)
                }
                false -> {
                    viewModel.removeBookmarkedBrewery(brewery!!)
                }
            }
        }
    }
}