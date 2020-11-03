package com.example.coachticketbooking.model

import com.google.gson.annotations.SerializedName

data class Ticket(
    val id: Int,
    @SerializedName("route_id")
    val routeId: Int,
    val date: String,
    val price: Float,
    @SerializedName("has_paid")
    val hasPaid: Int,
    @SerializedName("row_num")
    val rowNumber: Int,
    @SerializedName("column_num")
    val columnNumber: Int,
    @SerializedName("floor_num")
    val floorNumber: Int
)