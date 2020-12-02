package com.example.coachticketbooking.model

import com.google.gson.annotations.SerializedName

data class Ticket(
    val id: Int,
    @SerializedName("route_id")
    val routeId: Int,
    val date: String,
    val start: String,
    val end: String,
    @SerializedName("position_code")
    val positionCode: List<String>
)