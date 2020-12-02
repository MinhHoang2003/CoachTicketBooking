package com.example.coachticketbooking.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coachticketbooking.R
import com.example.coachticketbooking.model.Ticket
import kotlinx.android.synthetic.main.layout_ticket_item.view.*

class TicketAdapter : RecyclerView.Adapter<TicketAdapter.MyTicketHolder>() {

    private val mTickets = mutableListOf<Ticket>()
    private var mOnTicketClickListener: ((id: Int) -> Unit)? = null

    fun setData(tickets: List<Ticket>) {
        mTickets.clear()
        mTickets.addAll(tickets)
        notifyDataSetChanged()
    }

    fun setOnTicketClickListener(listener: (id: Int) -> Unit) {
        mOnTicketClickListener = listener
    }

    inner class MyTicketHolder(
        itemView: View,
        private val onTicketClickListener: ((id: Int) -> Unit)? = null
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val textTicketRoute = itemView.textTicketRoute
        private val textTicketDate = itemView.textTicketDate
        private val textTicketPosition = itemView.textTicketPositionCode

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(ticket: Ticket) {
            textTicketRoute.text = String.format("Tuyến xe:  %s -> %s", ticket.start, ticket.end)
            textTicketDate.text = String.format("Ngày : %s", ticket.date)
            val position =
                ticket.positionCode.toString().substring(1, ticket.positionCode.toString().length - 1)

            textTicketPosition.text = String.format("Chỗ ngồi : %s", position)
        }

        override fun onClick(v: View?) {
            onTicketClickListener?.invoke(mTickets[adapterPosition].id)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyTicketHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_ticket_item, parent, false)
        return MyTicketHolder(view, mOnTicketClickListener)
    }

    override fun onBindViewHolder(holder: MyTicketHolder, position: Int) {
        holder.bindView(mTickets[position])
    }

    override fun getItemCount(): Int = mTickets.size

}