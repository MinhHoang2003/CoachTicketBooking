package com.example.coachticketbooking.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocalPosition(
    @SerializedName("46")
    @Expose
    val coach42Position: List<List<String>>
)