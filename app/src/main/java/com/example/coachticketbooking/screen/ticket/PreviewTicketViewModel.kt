package com.example.coachticketbooking.screen.ticket

import androidx.lifecycle.MutableLiveData
import com.example.coachticketbooking.base.BaseViewModel
import com.example.coachticketbooking.base.DebugLog
import com.example.coachticketbooking.base.addToCompositeDisposable
import com.example.coachticketbooking.base.applyScheduler
import com.example.coachticketbooking.model.TicketLocalModel
import com.example.coachticketbooking.networking.RetrofitClient
import com.example.coachticketbooking.repository.ticket.ITicketRepository
import com.example.coachticketbooking.repository.ticket.TicketRepository

class PreviewTicketViewModel : BaseViewModel() {

    private val mTicketRepository: ITicketRepository by lazy {
        TicketRepository(RetrofitClient.getAPIService())
    }

    val ticketIdLiveDate: MutableLiveData<Int> = MutableLiveData(-1)
    val payTicketResult = MutableLiveData<Boolean>()

    fun createTicket(ticket: TicketLocalModel) {
        mTicketRepository.createTicket(ticket)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { ticketId, err ->
                if (err == null) {
                    ticketIdLiveDate.value = ticketId.toInt()
                } else {
                    mError.value = "Chỗ ngồi bạn chọn đã được một hành khách khác thanh toán trước. Vui lòng chọn lại chỗ ngồi"
                }
            }.addToCompositeDisposable(disposable)
    }

    fun payTicket(ticketId: Int) {
        mTicketRepository.payTicket(ticketId)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                payTicketResult.value = true
            }, {
                payTicketResult.value = false
            }).addToCompositeDisposable(disposable)
    }

    fun removeTempTicket(id: Int) {
        mTicketRepository.removeTicket(id)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                DebugLog.d("Remove ticket $id complete")
            }, {
                DebugLog.d("Remove err:  ticket $id : ${it.message}")
            }).addToCompositeDisposable(disposable)
    }

}