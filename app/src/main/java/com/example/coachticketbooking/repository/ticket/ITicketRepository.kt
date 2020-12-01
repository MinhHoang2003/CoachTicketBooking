package com.example.coachticketbooking.repository.ticket

import com.example.coachticketbooking.model.Ticket
import com.example.coachticketbooking.model.TicketLocalModel
import io.reactivex.rxjava3.core.Single

interface ITicketRepository {
    fun createTicket(ticket: TicketLocalModel): Single<String>
}