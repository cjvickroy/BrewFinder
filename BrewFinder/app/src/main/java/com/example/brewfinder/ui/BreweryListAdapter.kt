package com.example.brewfinder.ui

import android.telephony.PhoneNumberUtils
import android.util.Log
import com.example.brewfinder.data.Brewery
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.brewfinder.R

// removed private val onBreweryClick: (Brewery) -> Unit from instantiate

class BreweryListAdapter(private val onBreweryClick: (Brewery) -> Unit)
    : RecyclerView.Adapter<BreweryListAdapter.BreweryViewHolder>() {
    var breweryList = listOf<Brewery>()

    fun updateBreweryList(newBreweryList: List<Brewery>?) {
        breweryList = newBreweryList ?: listOf()
        Log.d("BreweryListAdapter", breweryList.toString())
        notifyDataSetChanged()
    }

    val breweryList1: MutableList<Brewery> = mutableListOf(
        Brewery("Block 15", "brewpub", "555-555-5550", "www.block15.com", "123 Main St", "Corvallis", "Oregon", "United States", "-123.259","44.561"),
        Brewery("Flat Tail", "closed", "555-555-5551", "www.flattail.com", "234 Main St", "Corvallis", "Oregon", "United States", "-123.259","44.561"),
        Brewery("Mazama", "brewpub", "555-555-5552", "www.mazama.com", "345 Main St", "Corvallis", "Oregon", "United States", "-123.259","44.561"),
        Brewery("Sky High", "micro", "555-555-5553", "www.skyhigh.com", "456 Main St", "Corvallis", "Oregon", "United States", "-123.259","44.561"),
        Brewery("Downward Dog", "brewpub", "555-555-5554", "www.downwarddog.com", "567 Main St", "Corvallis", "Oregon", "United States", "-123.259","44.561"),
        Brewery("McMenamins", "micro", "555-555-5555", "www.mcmenamins.com", "678 Main St", "Corvallis", "Oregon", "United States", "-123.259","44.561"),
        Brewery("Two Towns", "closed", "555-555-5556", "www.twotowns.com", "789 Main St", "Corvallis", "Oregon", "United States", "-123.259","44.561"),
        Brewery("Rob Hess Brewery", "micro", "555-555-5557", "www.hessisGOAT.com", "890 Main St", "Corvallis", "Oregon", "United States", "-123.259","44.561"),
        Brewery("BeerHaus", "closed", "555-555-5558", "www.beerhaus.com", "101 Main St", "Corvallis", "Oregon", "United States", "-123.259","44.561")
    )

    override fun getItemCount() = breweryList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.brewery_list_item, parent, false)
        //remove onBreweryClick from return
        return BreweryViewHolder(itemView, onBreweryClick)
    }

    override fun onBindViewHolder(holder: BreweryViewHolder, position: Int) {
        holder.bind(breweryList[position])
    }

    //removed as 2nd class param, val onClick: (Brewery) -> Unit
    class BreweryViewHolder(itemView: View, val onClick: (Brewery) -> Unit)
        : RecyclerView.ViewHolder(itemView) {
        private val nameTV: TextView = itemView.findViewById(R.id.tv_name)
        private val phoneTV: TextView = itemView.findViewById(R.id.tv_phone_number)
        private val typeTV: TextView = itemView.findViewById(R.id.tv_type)
        private val streetAddressTV: TextView = itemView.findViewById(R.id.tv_street_address)


        private var currentBrewery: Brewery? = null

        init {
            itemView.setOnClickListener {
                currentBrewery?.let(onClick)
            }
        }

        fun bind(brewery: Brewery) {
            currentBrewery = brewery
            nameTV.text = brewery.name
            phoneTV.text = brewery.phone
            typeTV.text = brewery.brewery_type.capitalize()
            streetAddressTV.text = brewery.street
        }
    }
}