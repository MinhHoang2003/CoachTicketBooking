package com.example.coachticketbooking.networking

import com.example.coachticketbooking.model.User
import com.example.coachticketbooking.model.UserLoginInformation
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {
    @GET("users/{phone_number}")
    fun getUserInformation(@Path("phone_number") phoneNumber: String): Single<List<User>>

    @POST("users/register/")
    fun register(@Body user: User): Completable

    @POST("users/login/")
    fun login(@Body userLoginInformation: UserLoginInformation): Completable
}