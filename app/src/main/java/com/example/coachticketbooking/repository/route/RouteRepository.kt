package com.example.coachticketbooking.repository.route

import com.example.coachticketbooking.model.Route
import com.example.coachticketbooking.networking.APIService
import io.reactivex.rxjava3.core.Single

class RouteRepository(private val apiService: APIService) : IRouteRepository {

    companion object {
        var INSTANCE: IRouteRepository? = null

        fun getInstance(apiService: APIService): IRouteRepository = INSTANCE ?: synchronized(this) {
            INSTANCE ?: RouteRepository(apiService).also { INSTANCE = it }
        }

    }

    override fun searchRoutes(
        pickLocation: String,
        destination: String,
        date: String
    ): Single<List<Route>> = apiService.searchRoute(pickLocation, destination, date)

}