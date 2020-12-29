package com.example.coachticketbooking.model

import com.example.coachticketbooking.utils.Constants
import com.example.coachticketbooking.utils.Utils

object UserData {
    var route: Route? = null
    var date: String = Constants.EMPTY_STRING
    var position: MutableList<Position> = mutableListOf()
    var pickLocation: Location? = null
    var destination: Location? = null
    var price: Int = 0

    var searchPattern: RouteSearchPattern? = null

    fun resetData() {
        position = mutableListOf()
        pickLocation = null
        destination = null
        price = 0
    }

    fun getPositionCode(): String {
        val code = position.map { it.positionCode }.toString()
        return code.substring(1, code.toString().length - 1)
    }

    fun getDateConverted(): String {
        return Utils.getServerDateFormat(date)
    }
}