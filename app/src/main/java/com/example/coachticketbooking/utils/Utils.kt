package com.example.coachticketbooking.utils

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
}