package com.example.coachticketbooking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coachticketbooking.R
import kotlinx.android.synthetic.main.layout_choose_location_item.view.*

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    private val mLocations = mutableListOf<String>()
    private var mCurrentLocation: Int = -1
    private var mOldLocation: Int = -1

    fun setData(data: List<String>) {
        mLocations.clear()
        mLocations.addAll(data)
        mOldLocation = -1
        mCurrentLocation = -1
        notifyDataSetChanged()
    }

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(location: String) {
            itemView.radioButtonCheck.isSelected = adapterPosition == mCurrentLocation
            itemView.textPosition.text = location
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_choose_location_item, parent, false)
        return LocationViewHolder(view)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {

    }

    override fun getItemCount(): Int = mLocations.size

}