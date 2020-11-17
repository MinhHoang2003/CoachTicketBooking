package com.example.coachticketbooking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coachticketbooking.R
import com.example.coachticketbooking.model.Route
import kotlinx.android.synthetic.main.layout_route_items.view.*

class RouteAdapter : RecyclerView.Adapter<RouteAdapter.RouteViewHolder>() {

    private val routes = mutableListOf<Route>()
    var onItemClick: ((id: Int) -> Unit)? = null

    fun setData(routes: List<Route>) {
        this.routes.clear()
        this.routes.addAll(routes)
        notifyDataSetChanged()
    }

    inner class RouteViewHolder(itemView: View, private val onItemClick: ((id: Int) -> Unit)? = null) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val textStartTime: TextView = itemView.textStartTime
        private val textStartLocation: TextView = itemView.textStartLocation
        private val textEndTime: TextView = itemView.textEndTime
        private val textEndLocation: TextView = itemView.textEndLocation
        private val textPrice: TextView = itemView.textPrice
        private val textPositionNumber: TextView = itemView.textPositionNumber
        private val textRemainingPositionNumber: TextView = itemView.textRemainingPositionNumber

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(route: Route) {
            textStartTime.text = route.startTime
            textStartLocation.text = route.startAddress
            textEndTime.text = route.endTime
            textEndLocation.text = route.endAddress
            textPrice.text = String.format("%s d", route.price.toString())
            textPositionNumber.text =
                String.format("Giường nằm %s chỗ", route.numberPosition.toString())
            textRemainingPositionNumber.text = String.format("(Còn %s chỗ)", route.remain)
        }

        override fun onClick(v: View?) {
            onItemClick?.invoke(routes[adapterPosition].id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_route_items, parent, false)
        return RouteViewHolder(itemView, onItemClick)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.bindView(routes[position])
    }

    override fun getItemCount(): Int = routes.size

}