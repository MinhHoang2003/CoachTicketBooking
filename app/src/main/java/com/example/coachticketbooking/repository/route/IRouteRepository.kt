package com.example.coachticketbooking.repository.route

import com.example.coachticketbooking.model.Route
import io.reactivex.rxjava3.core.Single

interface IRouteRepository {
    fun searchRoutes(pickLocation: String, destination: String, date: String): Single<List<Route>>
}