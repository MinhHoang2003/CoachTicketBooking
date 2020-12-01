package com.example.coachticketbooking.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("phone_number")
    val phoneNumber: String,
    val name: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val email: String,
    @Expose(serialize = true, deserialize = false)
    val password : String,
    val address: String,
    val role: Int
)