package com.example.coachticketbooking.model

import com.google.gson.annotations.SerializedName

data class Route(
    val id: Int,
    @SerializedName("coach_id")
    val coachId: String,
    @SerializedName("start_time")
    //TODO : on server start time and end time is Time ( object type) need to check before get
    val startTime : String,
    @SerializedName("estimate_end_time")
    val endTime : String
)