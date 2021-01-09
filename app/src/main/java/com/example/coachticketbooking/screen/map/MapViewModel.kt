package com.example.coachticketbooking.screen.map

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbooking.base.BaseViewModel
import com.example.coachticketbooking.base.addToCompositeDisposable
import com.example.coachticketbooking.base.applyScheduler
import com.example.coachticketbooking.model.Location
import com.example.coachticketbooking.networking.RetrofitClient
import com.example.coachticketbooking.repository.location.LocationRepository

class MapViewModel : BaseViewModel() {

    private val mLocationRepository by lazy {
        LocationRepository.getInstance(RetrofitClient.getAPIService())
    }

    val location = MutableLiveData<List<Location>>()

    fun getLocation(routeId: Int) {
        mLocationRepository.getAll(routeId)
            .applyScheduler()
            .subscribe { locations, err ->
                if (err == null) {
                    location.value = locations
                }
            }
            .addToCompositeDisposable(disposable)
    }
}