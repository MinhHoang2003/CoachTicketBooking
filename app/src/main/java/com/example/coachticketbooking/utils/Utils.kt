package com.example.coachticketbooking.utils

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    private const val DATE_FORMAT_APP_PATTERN = "dd MMM, yyyy"
    private const val DATE_FORMAT_SERVER_PATTERN = "yyyy-MM-dd"
    private const val CURRENCY_FORMAT_PATTERN = "#,###"

    const val PHONE_NUMBER = "0123456789"
    const val EMAIL = "coachticketbooking@gmail.com"

    fun getCurrentTime(): String {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT_APP_PATTERN, Locale.ENGLISH)
        return simpleDateFormat.format(Date())
    }

    fun parseTime(time: Date): String {
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT_APP_PATTERN, Locale.ENGLISH)
        return simpleDateFormat.format(time)
    }

    fun getMaxDate(): Long {
        val c = Calendar.getInstance()
        c.time = Date()
        c.add(Calendar.MONTH, 1)
        return c.time.time
    }

    fun getCurrencyFormat(money: Int): String {
        val formatter = DecimalFormat(CURRENCY_FORMAT_PATTERN)
        return formatter.format(money)
    }

    fun getServerDateFormat(date: String): String {
        val currentDateFormat = SimpleDateFormat(DATE_FORMAT_APP_PATTERN, Locale.ENGLISH)
        val currentDate = currentDateFormat.parse(date)
        val networkDateFormat = SimpleDateFormat(DATE_FORMAT_SERVER_PATTERN, Locale.ENGLISH)
        return networkDateFormat.format(currentDate ?: Constants.EMPTY_STRING)
    }

    fun parseLocalDateFormat(date: String): String {
        val currentDateFormat = SimpleDateFormat(DATE_FORMAT_SERVER_PATTERN, Locale.ENGLISH)
        val currentDate = currentDateFormat.parse(date)
        val networkDateFormat = SimpleDateFormat(DATE_FORMAT_APP_PATTERN, Locale.ENGLISH)
        return networkDateFormat.format(currentDate ?: Constants.EMPTY_STRING)
    }

    fun increaseNextDay(date: String): String {
        val currentDateFormat = SimpleDateFormat(DATE_FORMAT_APP_PATTERN, Locale.ENGLISH)
        var currentDate = currentDateFormat.parse(date)
        val c = Calendar.getInstance()
        c.time = currentDate
        c.add(Calendar.DATE, 1)
        currentDate = c.time
        return currentDateFormat.format(currentDate)
    }

    fun decreasePreviousDay(date: String): String {
        val currentDateFormat = SimpleDateFormat(DATE_FORMAT_APP_PATTERN, Locale.ENGLISH)
        var currentDate = currentDateFormat.parse(date)
        val c = Calendar.getInstance()
        c.time = currentDate
        c.add(Calendar.DATE, -1)
        currentDate = c.time
        return currentDateFormat.format(currentDate)
    }
}