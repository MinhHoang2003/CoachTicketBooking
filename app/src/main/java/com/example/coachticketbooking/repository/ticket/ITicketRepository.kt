package com.example.coachticketbooking.repository.ticket

import com.example.coachticketbooking.model.Ticket
import com.example.coachticketbooking.model.TicketDetail
import com.example.coachticketbooking.model.TicketLocalModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ITicketRepository {
    fun createTicket(ticket: TicketLocalModel): Single<String>
    fun getMyTickets(phoneNumber: String): Single<List<Ticket>>
    fun getTicketDetail(id: Int): Single<TicketDetail>
    fun payTicket(id: Int): Completable
    fun removeTicket(id: Int): Completable
}