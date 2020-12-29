package com.example.coachticketbooking.screen.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.base.view.disable
import com.example.coachticketbooking.base.view.enable
import com.example.coachticketbooking.dialog.DatePickerBottomSheet
import com.example.coachticketbooking.model.UserData
import com.example.coachticketbooking.screen.MainActivity
import com.example.coachticketbooking.utils.Utils
import kotlinx.android.synthetic.main.home_fragment.*
import okhttp3.internal.Util
import java.util.*

class HomeFragment : BaseFragment(), View.OnClickListener {

    companion object {
        const val KEY_PICK_LOCATION = "Pick Location"
        const val KEY_DESTINATION = "Destination"
        const val KEY_DATE = "Date"
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun getLayoutId(): Int = R.layout.home_fragment

    override fun initView() {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//        btnFindRoute.disable()
        datePickerContainer.setOnClickListener(this)
        btnFindRoute.setOnClickListener(this)
        edtPickLocation.addTextChangedListener {
            checkEnableButtonFind()
        }

        edtDestination.addTextChangedListener {
            checkEnableButtonFind()
        }

        calendarView.minDate = Date().time
        calendarView.date = Date().time
        calendarView.maxDate = Utils.getMaxDate()
    }

    override fun initData(bundle: Bundle?) {
        textDate.text = Utils.getCurrentTime()
        UserData.resetData()
        UserData.date = Utils.getCurrentTime()
    }

    override fun initObserver() {
        //Nothing
    }

    override fun initListener() {
        calendarView.setOnDateChangeListener { _, year, month, day ->
            val c = Calendar.getInstance()
            c.set(year, month - 1, day)
            textDate.text = Utils.parseTime(c.time)
            UserData.date = Utils.parseTime(c.time)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            datePickerContainer -> {
                val datePickerBottomSheet = DatePickerBottomSheet()
                fragmentManager?.let { manager -> datePickerBottomSheet.show(manager, "TAG") }
            }

            btnFindRoute -> {
                val pickLocation = edtPickLocation.text.toString()
                val destination = edtDestination.text.toString()
                val date = textDate.text
                Intent(context, MainActivity::class.java).apply {
                    putExtra(KEY_PICK_LOCATION, pickLocation)
                    putExtra(KEY_DESTINATION, destination)
                    putExtra(KEY_DATE, date)
                    startActivity(this)
                }
            }
        }
    }

    private fun checkEnableButtonFind() {
        val pickLocation = edtPickLocation.text.toString()
        val destination = edtDestination.text.toString()
        if (pickLocation.isNotBlank() && destination.isNotBlank()) {
            btnFindRoute.enable()
        } else btnFindRoute.disable()
    }
}
