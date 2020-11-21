package com.example.coachticketbooking.repository.location

import com.example.coachticketbooking.model.Location
import io.reactivex.rxjava3.core.Single

interface ILocationRepository {
    fun getPickLocation(routeId: Int): Single<List<Location>>
    fun getDestination(routeId: Int): Single<List<Location>>
}