package com.example.coachticketbooking.screen.choose_location.choose_location_info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coachticketbooking.base.BaseViewModel
import com.example.coachticketbooking.base.addToCompositeDisposable
import com.example.coachticketbooking.base.applyScheduler
import com.example.coachticketbooking.model.Location
import com.example.coachticketbooking.networking.RetrofitClient
import com.example.coachticketbooking.repository.location.LocationRepository

class ChoosePickLocationViewModel : BaseViewModel() {
    private val mLocationRepository by lazy {
        LocationRepository.getInstance(RetrofitClient.getAPIService())
    }

    val pickLocationLiveData: MutableLiveData<List<Location>> = MutableLiveData(arrayListOf())
    val destinationLocationLiveData: MutableLiveData<List<Location>> =
        MutableLiveData(arrayListOf())

    fun getPickLocationOfRoute(routeId: Int) {
        mLocationRepository.getPickLocation(routeId)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { locations, err ->
                if (err == null) {
                    pickLocationLiveData.value = locations
                } else mError.value = err.message
            }.addToCompositeDisposable(disposable)
    }

    fun getDestinationLocationOfRoute(routeId: Int) {
        mLocationRepository.getDestination(routeId)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { locations, err ->
                if (err == null) {
                    destinationLocationLiveData.value = locations
                } else mError.value = err.message
            }.addToCompositeDisposable(disposable)
    }


}