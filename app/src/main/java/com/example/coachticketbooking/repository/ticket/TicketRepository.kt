package com.example.coachticketbooking.repository.ticket

import com.example.coachticketbooking.model.Ticket
import com.example.coachticketbooking.model.TicketDetail
import com.example.coachticketbooking.model.TicketLocalModel
import com.example.coachticketbooking.networking.APIService
import io.reactivex.rxjava3.core.Single

class TicketRepository(private val apiService: APIService) : ITicketRepository {
    override fun createTicket(ticket: TicketLocalModel): Single<String> =
        apiService.createTicket(ticket)

    override fun getMyTickets(phoneNumber: String): Single<List<Ticket>> =
        apiService.getMyTickets(phoneNumber)

    override fun getTicketDetail(id: Int): Single<TicketDetail> = apiService.getTicketDetail(id)

}