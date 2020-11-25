package com.example.coachticketbooking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coachticketbooking.R
import com.example.coachticketbooking.model.Location
import kotlinx.android.synthetic.main.layout_choose_location_item.view.*

class LocationAdapter : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    private val mLocations = mutableListOf<Location>()
    private var mCurrentLocation: Int = -1
    private var mOldLocation: Int = -1
    var onLocationClickListener: ((location: Location) -> Unit)? = null

    fun setData(data: List<Location>) {
        mLocations.clear()
        mLocations.addAll(data)
        mOldLocation = -1
        mCurrentLocation = -1
        notifyDataSetChanged()
    }

    fun setCurrentLocation(location: Location) {
        mCurrentLocation = mLocations.indexOf(location)
        notifyItemChanged(mCurrentLocation)
    }

    inner class LocationViewHolder(itemView: View, private val listener: ((Location) -> Unit)?) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(location: Location) {
            itemView.radioButtonCheck.isChecked = adapterPosition == mCurrentLocation
            itemView.textPosition.text = location.city
        }

        override fun onClick(v: View?) {
            mOldLocation = mCurrentLocation
            mCurrentLocation = adapterPosition
            notifyItemChanged(adapterPosition)
            notifyItemChanged(mOldLocation)
            listener?.invoke(mLocations[adapterPosition])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_choose_location_item, parent, false)
        return LocationViewHolder(view, onLocationClickListener)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bindView(mLocations[position])
    }

    override fun getItemCount(): Int = mLocations.size

}