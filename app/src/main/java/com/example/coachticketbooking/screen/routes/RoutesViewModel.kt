package com.example.coachticketbooking.screen.routes

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbooking.base.BaseViewModel
import com.example.coachticketbooking.base.addToCompositeDisposable
import com.example.coachticketbooking.base.applyScheduler
import com.example.coachticketbooking.model.Route
import com.example.coachticketbooking.networking.RetrofitClient
import com.example.coachticketbooking.repository.route.IRouteRepository
import com.example.coachticketbooking.repository.route.RouteRepository

class RoutesViewModel : BaseViewModel() {
    private val routeRepository: IRouteRepository by lazy {
        RouteRepository.getInstance(
            RetrofitClient.getAPIService()
        )
    }

    val routesLiveData: MutableLiveData<List<Route>> = MutableLiveData(listOf())

    fun searchRoutes(pickLocation: String, destination: String, date: String) {
        routeRepository.searchRoutes(pickLocation, destination, date)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                routesLiveData.value = it
            }, {
                mError.value = it.message
            }).addToCompositeDisposable(disposable)
    }
}
