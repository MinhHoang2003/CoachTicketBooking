package com.example.coachticketbooking.model

import com.google.gson.annotations.SerializedName

data class Position(
//    val id: Int,
//    @SerializedName("route_id")
//    val routeId: Int,
//    val date: String,
    @SerializedName("has_paid")
    var hasPaid: Int,
//    @SerializedName("ticket_id")
//    val ticketId: Int,
    @SerializedName("position_code")
    val positionCode: String,
)