package com.example.coachticketbooking.model

import com.google.gson.annotations.SerializedName

data class Location(
    val id: Int,
    @SerializedName("route_id")
    val routeId: Int,
    val ordering: Int,
    @SerializedName("stop_station_type")
    val stopStationType: Int,
    val city: String,
    @SerializedName("detail_location")
    val detailLocation: String,
    val longitude: Float,
    val latitude: Float,
    val time : String
)