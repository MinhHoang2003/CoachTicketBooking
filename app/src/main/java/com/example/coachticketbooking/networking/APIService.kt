package com.example.coachticketbooking.networking

import com.example.coachticketbooking.model.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface APIService {

    @POST("users/register/")
    fun register(@Body user: User): Completable

    @POST("users/login/")
    fun login(@Body userLoginInformation: UserLoginInformation): Single<User>

    @GET("users/detail")
    fun getUser(@Query("id") id: String): Single<User>

    @PUT("users/update")
    fun updateUser(@Body user: User, @Query("id") phoneNumber: String): Completable

    @PUT("users/updateWithPassword")
    fun updateUserWithPassword(
        @Body user: User,
        @Query("password") pass: String,
        @Query("id") phoneNumber: String
    ): Completable

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

    @GET("locations/routes/")
    fun getLocations(@Query("route_id") id: Int): Single<List<Location>>

    @GET("locations/destination")
    fun getDestinationLocation(@Query("route_id") id: Int): Single<List<Location>>

    @POST("tickets/")
    fun createTicket(@Body ticket: TicketLocalModel): Single<String>

    @GET("tickets")
    fun getMyTickets(@Query("phone_number") phoneNumber: String): Single<List<Ticket>>

    @GET("tickets/detail")
    fun getTicketDetail(@Query("id") id : Int): Single<TicketDetail>
}