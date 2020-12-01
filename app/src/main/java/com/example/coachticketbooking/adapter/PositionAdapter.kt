package com.example.coachticketbooking.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.DebugLog
import com.example.coachticketbooking.model.Position
import com.example.coachticketbooking.utils.Constants
import kotlinx.android.synthetic.main.layout_position_item.view.*

class PositionAdapter(val context: Context) :
    RecyclerView.Adapter<PositionAdapter.PositionViewHolder>() {

    companion object {
        const val ACTION_CHOOSE_POSITION = "CHOOSE_POSITION"
    }

    private val positions = mutableListOf<Position>()
    val selectedPositions = mutableListOf<Position>()
    var onItemClick: ((id: String) -> Unit)? = null
    var onSelectedChangeListener: ((code: List<String>) -> Unit)? = null

    fun setData(positions: List<Position>) {
        this.positions.clear()
        this.positions.addAll(positions)
        notifyDataSetChanged()
    }

    fun addSelectionPosition(selections: List<Position>) {
        selectedPositions.clear()
        selectedPositions.addAll(selections)
        notifyDataSetChanged()
        onSelectedChangeListener?.invoke(selectedPositions.map { selectedPosition -> selectedPosition.positionCode })
    }

    fun addSelections(position: Position) {
        selectedPositions.add(position)
        onSelectedChangeListener?.invoke(selectedPositions.map { selectedPosition -> selectedPosition.positionCode })
    }

    fun removeSelections(position: Position) {
        selectedPositions.remove(position)
        onSelectedChangeListener?.invoke(selectedPositions.map { selectedPosition -> selectedPosition.positionCode })
    }

    inner class PositionViewHolder(
        itemView: View,
        private val onItemClick: ((id: String) -> Unit)? = null
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val textPositionCode: TextView = itemView.textCode
        private val backgroundPosition: LinearLayout = itemView.backgroundPosition

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = positions[adapterPosition]
            if (position.hasPaid == 1 || position.positionCode == Constants.EMPTY_STRING) return
            notifyItemChanged(adapterPosition, ACTION_CHOOSE_POSITION)
            onItemClick?.invoke(position.positionCode)
        }

        fun bindView(position: Position) {
            when {
                position.positionCode == Constants.EMPTY_STRING -> {
                    backgroundPosition.background = null
                    textPositionCode.text = Constants.EMPTY_STRING
                }
                position.hasPaid == 1 -> {
                    textPositionCode.text = position.positionCode
                    backgroundPosition.background =
                        ContextCompat.getDrawable(context, R.drawable.background_selected_position)
                }
                else -> {
                    textPositionCode.text = position.positionCode
                    backgroundPosition.background =
                        ContextCompat.getDrawable(
                            context,
                            R.drawable.background_not_selected_position
                        )
                }
            }
            if (selectedPositions.contains(position)) {
                showSelectionState(true)
            } else if (position.positionCode != Constants.EMPTY_STRING && position.hasPaid != 1) {
                showSelectionState(false)
            }
        }

        fun changedSelection(position: Int) {
            val p = positions[position]
            if (selectedPositions.contains(p)) {
                removeSelections(p)
                showSelectionState(false)
            } else {
                addSelections(p)
                showSelectionState(true)
            }
        }

        private fun showSelectionState(isSelected: Boolean) {
            if (isSelected) {
                backgroundPosition.background =
                    ContextCompat.getDrawable(context, R.drawable.background_select_position)
            } else {
                backgroundPosition.background =
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.background_not_selected_position
                    )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PositionViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_position_item, parent, false)
        return PositionViewHolder(itemView, onItemClick)
    }

    override fun onBindViewHolder(holder: PositionViewHolder, position: Int) {
        holder.bindView(positions[position])
    }

    override fun onBindViewHolder(
        holder: PositionViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            holder.bindView(positions[position])
        } else {
            payloads.forEach {
                when (it) {
                    ACTION_CHOOSE_POSITION -> holder.changedSelection(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = positions.size
}