package com.example.coachticketbooking.screen.my_ticket

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbooking.base.BaseViewModel
import com.example.coachticketbooking.base.addToCompositeDisposable
import com.example.coachticketbooking.base.applyScheduler
import com.example.coachticketbooking.model.Ticket
import com.example.coachticketbooking.networking.RetrofitClient
import com.example.coachticketbooking.repository.ticket.ITicketRepository
import com.example.coachticketbooking.repository.ticket.TicketRepository

class MyTicketViewModel : BaseViewModel() {

    private val mTicketRepository: ITicketRepository by lazy {
        TicketRepository(RetrofitClient.getAPIService())
    }

    val myTicketsLiveData = MutableLiveData<List<Ticket>>(arrayListOf())

    fun getMyTickets(phoneNumber: String) {
        mTicketRepository.getMyTickets(phoneNumber)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { tickets, err ->
                if (err == null) {
                    myTicketsLiveData.value = tickets
                }
            }
            .addToCompositeDisposable(disposable)
    }

}