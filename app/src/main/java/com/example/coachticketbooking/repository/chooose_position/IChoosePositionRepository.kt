package com.example.coachticketbooking.repository.chooose_position

import com.example.coachticketbooking.model.Position
import io.reactivex.rxjava3.core.Single

interface IChoosePositionRepository {
    fun getPositionOfRoute(routeId: Int, date: String): Single<List<Position>>
}