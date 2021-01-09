package com.example.coachticketbooking.repository.location

import com.example.coachticketbooking.model.Location
import com.example.coachticketbooking.networking.APIService
import io.reactivex.rxjava3.core.Single

class LocationRepository(private val apiService: APIService) : ILocationRepository {

    companion object {
        private var INSTANCE: ILocationRepository? = null

        fun getInstance(apiService: APIService): ILocationRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: LocationRepository(apiService).also { INSTANCE = it }
            }

    }

    override fun getPickLocation(routeId: Int): Single<List<Location>> =
        apiService.getPickLocation(routeId)

    override fun getDestination(routeId: Int): Single<List<Location>> =
        apiService.getDestinationLocation(routeId)

    override fun getAll(routeId: Int): Single<List<Location>> = apiService.getLocations(routeId)

}