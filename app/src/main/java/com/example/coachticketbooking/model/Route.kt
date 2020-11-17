package com.example.coachticketbooking.model

import com.google.gson.annotations.SerializedName

data class Route(
    val id: Int,
    @SerializedName("coach_id")
    val coachId: String,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("estimate_end_time")
    val endTime: String,
    @SerializedName("number_position")
    val numberPosition: Int,
    @SerializedName("start_address")
    val startAddress: String,
    @SerializedName("end_address")
    val endAddress: String,
    val price: Int,
    val remain: Int
)