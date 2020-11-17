package com.example.coachticketbooking.utils

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getCurrentTime(): String {
        val simpleDateFormat = SimpleDateFormat("dd MMMM, yyyy", Locale.ENGLISH)
        return simpleDateFormat.format(Date())
    }

}