package com.example.coachticketbooking.utils

import com.example.coachticketbooking.model.UserData
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getCurrentTime(): String {
        val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH)
        return simpleDateFormat.format(Date())
    }

    fun getCurrencyFormat(money: Int): String {
        val formatter = DecimalFormat("#,###")
        return formatter.format(money)
    }

    fun getServerDateFormat(date: String): String {
        val currentDateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH)
        val currentDate = currentDateFormat.parse(date)
        val networkDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return networkDateFormat.format(currentDate ?: Constants.EMPTY_STRING)
    }

    fun parseLocalDateFormat(date: String): String {
        val currentDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val currentDate = currentDateFormat.parse(date)
        val networkDateFormat = SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH)
        return networkDateFormat.format(currentDate ?: Constants.EMPTY_STRING)
    }
}