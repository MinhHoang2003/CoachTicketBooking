package com.example.coachticketbooking.model

import com.google.gson.annotations.SerializedName

data class StopStation(
    val id: Int,
    @SerializedName("route_id")
    val routeId: Int,
    val address: String,
    val longitude: Float,
    val latitude: Float
)