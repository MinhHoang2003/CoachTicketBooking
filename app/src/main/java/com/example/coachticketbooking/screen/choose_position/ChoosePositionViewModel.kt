package com.example.coachticketbooking.screen.choose_position

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.coachticketbooking.base.BaseViewModel
import com.example.coachticketbooking.base.addToCompositeDisposable
import com.example.coachticketbooking.base.applyScheduler
import com.example.coachticketbooking.model.Position
import com.example.coachticketbooking.model.converter.PositionConverter
import com.example.coachticketbooking.networking.RetrofitClient
import com.example.coachticketbooking.repository.chooose_position.ChoosePositionRepository
import com.example.coachticketbooking.repository.chooose_position.IChoosePositionRepository
import com.example.coachticketbooking.utils.Constants
import com.example.coachticketbooking.utils.FileManager

class ChoosePositionViewModel(
    context: Context
) : BaseViewModel() {

    private val mChoosePositionRepository: IChoosePositionRepository by lazy {
        ChoosePositionRepository.getInstance(
            RetrofitClient.getAPIService()
        )
    }

    private val fileManager = FileManager.getInstance(context)
    var positionsLiveData: MutableLiveData<List<Position>> = MutableLiveData(arrayListOf())
    var currentRouteId: Int = -1
    var currentDate: String = Constants.EMPTY_STRING

    fun getPositions(routeId: Int, date: String, floor: Int = 1, numberPositionOfCoach: Int = 46) {
        val localPositions = mutableListOf<Position>()
        fileManager.readPositionLocal()
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { positions, err ->
                if (err == null) {
                    val positionCode =
                        if (numberPositionOfCoach == 46) positions.coach46Position[floor - 1] else positions.coach29Position

                    val positionConverted = PositionConverter.convertLocalPositionToPosition(positionCode)
                    localPositions.addAll(positionConverted)
                    mChoosePositionRepository.getPositionOfRoute(routeId, date)
                        .applyScheduler()
                        .subscribe { networkPositions, networkErr ->
                            if (networkErr == null) {
                                localPositions.forEach { localPosition ->
                                    networkPositions.forEach { networkPosition ->
                                        if (networkPosition.positionCode == localPosition.positionCode) {
                                            localPosition.hasPaid = networkPosition.hasPaid
                                        }
                                    }
                                }
                                positionsLiveData.value = localPositions
                            } else mError.value = networkErr.message
                        }
                } else {
                    mError.value = err.message
                }
            }
            .addToCompositeDisposable(disposable)
    }

}