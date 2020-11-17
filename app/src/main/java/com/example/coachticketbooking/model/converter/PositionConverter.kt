package com.example.coachticketbooking.model.converter

import com.example.coachticketbooking.model.Position

object PositionConverter {
    fun convertLocalPositionToPosition(localPosition: List<String>): List<Position> {
        return localPosition.map { sitCode ->
            Position(hasPaid = 0, positionCode = sitCode)
        }
    }
}