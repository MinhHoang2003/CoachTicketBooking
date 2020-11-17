package com.example.coachticketbooking.repository.chooose_position

import com.example.coachticketbooking.model.Position
import com.example.coachticketbooking.networking.APIService
import com.example.coachticketbooking.repository.route.IRouteRepository
import com.example.coachticketbooking.repository.route.RouteRepository
import io.reactivex.rxjava3.core.Single

class ChoosePositionRepository(private val apiService: APIService) : IChoosePositionRepository {

    companion object {
        var INSTANCE: IChoosePositionRepository? = null

        fun getInstance(apiService: APIService): IChoosePositionRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ChoosePositionRepository(apiService).also { INSTANCE = it }
            }

    }


    override fun getPositionOfRoute(
        routeId: Int,
        date: String
    ): Single<List<Position>> = apiService.getPosition(routeId, date)
}