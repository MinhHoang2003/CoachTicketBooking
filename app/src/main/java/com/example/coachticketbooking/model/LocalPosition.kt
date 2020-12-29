package com.example.coachticketbooking.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocalPosition(
    @SerializedName("46")
    @Expose
    val coach46Position: List<List<String>>,
    @SerializedName("29")
    @Expose
    val coach29Position: List<String>
)