package com.example.coachticketbooking.utils

import com.example.coachticketbooking.model.LocalPosition
import io.reactivex.rxjava3.core.Single

interface IFileManager {

    companion object {
        const val POSITION_PATH = "position.json"
    }

    fun readPositionLocal(): Single<LocalPosition>

}