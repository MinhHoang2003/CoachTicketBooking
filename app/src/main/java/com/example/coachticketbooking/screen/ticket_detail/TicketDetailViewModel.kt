package com.example.coachticketbooking.screen.ticket_detail

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbooking.base.BaseViewModel
import com.example.coachticketbooking.base.addToCompositeDisposable
import com.example.coachticketbooking.base.applyScheduler
import com.example.coachticketbooking.model.TicketDetail
import com.example.coachticketbooking.networking.RetrofitClient
import com.example.coachticketbooking.repository.ticket.ITicketRepository
import com.example.coachticketbooking.repository.ticket.TicketRepository

class TicketDetailViewModel : BaseViewModel() {
    private val mTicketRepository: ITicketRepository by lazy {
        TicketRepository(RetrofitClient.getAPIService())
    }

    val ticketDetailLiveData: MutableLiveData<TicketDetail> = MutableLiveData()

    fun getTicketDetail(id: Int) {
        mTicketRepository.getTicketDetail(id)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { tickets, err ->
                if (err == null) {
                    ticketDetailLiveData.value = tickets
                }
            }.addToCompositeDisposable(disposable)
    }
}