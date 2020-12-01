package com.example.coachticketbooking.networking

import com.example.coachticketbooking.model.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface APIService {
    @GET("users/{phone_number}")
    fun getUserInformation(@Path("phone_number") phoneNumber: String): Single<List<User>>

    @POST("users/register/")
    fun register(@Body user: User): Completable

    @POST("users/login/")
    fun login(@Body userLoginInformation: UserLoginInformation): Single<List<User>>

    @GET("routes/search")
    fun searchRoute(
        @Query("pick_location") pickLocation: String,
        @Query("destination") destination: String,
        @Query("date") date: String
    ): Single<List<Route>>

    @GET("routes/position")
    fun getPosition(@Query("route_id") id: Int, @Query("date") date: String): Single<List<Position>>

    @GET("locations/pick")
    fun getPickLocation(@Query("route_id") id: Int): Single<List<Location>>

    @GET("locations/destination")
    fun getDestinationLocation(@Query("route_id") id: Int): Single<List<Location>>

    @POST("tickets/")
    fun createTicket(@Body ticket: TicketLocalModel): Single<String>
}