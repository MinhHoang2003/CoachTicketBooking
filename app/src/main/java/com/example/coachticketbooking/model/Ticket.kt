package com.example.coachticketbooking.model

import com.google.gson.annotations.SerializedName

data class Ticket(
    val id: Int,
    @SerializedName("route_id")
    val routeId: Int,
    val date: String,
    val price: Int,
    @SerializedName("has_paid")
    val hasPaid: Int,
    @SerializedName("pick_id")
    val pickLocationId : Int,
    @SerializedName("destination_id")
    val destinationId : Int
)