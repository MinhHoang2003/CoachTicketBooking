package com.example.coachticketbooking.model

import com.google.gson.annotations.SerializedName

data class TicketDetail(
    val id: Int,
    @SerializedName("route_id")
    val routeId: Int,
    val date: String,
    @SerializedName("has_paid")
    val hasPaid: Int,
    val price: Int,
    @SerializedName("user_id")
    val useId: String,
    val start: String,
    val end: String,
    @SerializedName("pick_location")
    val pickLocation: Location,
    @SerializedName("destination_location")
    val destination: Location,
    @SerializedName("position_code")
    val positionCode : List<String>
)