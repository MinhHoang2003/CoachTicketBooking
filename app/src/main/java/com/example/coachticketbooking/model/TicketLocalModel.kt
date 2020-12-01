package com.example.coachticketbooking.model

import com.google.gson.annotations.SerializedName

data class TicketLocalModel(
    @SerializedName("route_id")
    val routeId: Int,
    val date: String,
    val price: Int,
    @SerializedName("has_paid")
    val hasPaid: Int,
    @SerializedName("pick_id")
    var pickLocationId: Int = -1,
    @SerializedName("destination_id")
    var destinationId: Int = -1,
    @SerializedName("position_code")
    val positionCode: List<String>,
    @SerializedName("user_id")
    val userId: String
)