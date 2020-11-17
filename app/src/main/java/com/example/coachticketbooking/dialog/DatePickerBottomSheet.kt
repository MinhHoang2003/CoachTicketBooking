package com.example.coachticketbooking.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.DebugLog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_bottom_sheet_date_picker.*
import java.util.*

class DatePickerBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_bottom_sheet_date_picker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        calendarView.minDate = Date().time
        calendarView.date = Date().time
        calendarView.setOnDateChangeListener { _, year, month, day ->
            DebugLog.e("Hoang on set date changed listener : $year, $month, $day")
        }

        btnApplyDate.setOnClickListener {
            this.dismiss()
        }
    }

}