package com.example.coachticketbooking.model

import com.google.gson.annotations.SerializedName

data class Coach(
    @SerializedName("coach_id")
    val id: String,
    @SerializedName("number_position")
    val numberPosition: Int,
    @SerializedName("number_floors")
    val numberFloor: Int,
    @SerializedName("driver_id")
    val driverId: String,
    @SerializedName("car_brand")
    val carBrand: String,
    val images: String,
    val rate: Float
)