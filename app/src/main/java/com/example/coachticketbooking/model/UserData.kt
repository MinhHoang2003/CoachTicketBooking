package com.example.coachticketbooking.model

import com.example.coachticketbooking.utils.Constants

object UserData {
    var date: String = Constants.EMPTY_STRING
    var position: MutableList<Position> = mutableListOf()
    var routeId: Int = -1
    var pickLocation: Location? = null
    var destination: Location? = null
    var price: Int = 0
}