package com.example.coachticketbooking.screen.ticket_detail

import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.dialog.DialogTicketQRPreview
import com.example.coachticketbooking.model.TicketDetail
import com.example.coachticketbooking.utils.Utils
import kotlinx.android.synthetic.main.ticket_detail_fragment.*
import net.glxn.qrgen.android.QRCode


class TicketDetailFragment : BaseFragment() {

    companion object {
        const val KEY_TICKET_ID = "TicketId"
        fun newInstance(id: Int) = TicketDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(KEY_TICKET_ID, id)
            }
        }
    }

    private var mTicketId: Int? = null
    private var mTicketQR: Bitmap? = null
    private lateinit var mTicketDetailViewModel: TicketDetailViewModel
    override fun getLayoutId(): Int = R.layout.ticket_detail_fragment

    override fun initView() {
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
        toolbar.title = getString(R.string.previewTicketScreenTitle)
    }

    override fun initData(bundle: Bundle?) {
        mTicketDetailViewModel = ViewModelProvider(this).get(TicketDetailViewModel::class.java)
        bundle?.apply {
            val ticketId = getInt(KEY_TICKET_ID)
            mTicketDetailViewModel.getTicketDetail(ticketId)
        }
    }

    override fun initObserver() {
        mTicketDetailViewModel.ticketDetailLiveData.observe(this, {
            mTicketId = it.id
            mTicketQR = QRCode.from(mTicketId.toString()).bitmap()
            showData(ticket = it)
        })

        mTicketDetailViewModel.mLoading.observe(this, {
            if (it) showLoading()
            else hideLoading()
        })
    }

    override fun initListener() {
        btnQR.setOnClickListener {
            val previewTicketDialog = context?.let { context -> DialogTicketQRPreview(context) }
            mTicketId?.let {
                previewTicketDialog?.showDialog(mTicketQR ?: QRCode.from(it.toString()).bitmap())
            }
        }
        toolbar.setNavigationOnClickListener { popBackStack() }
    }

    private fun showData(ticket: TicketDetail) {
        textSum.text = String.format("%sd", Utils.getCurrencyFormat(ticket.price))
        textRoute.text = String.format(
            "Tuyến xe: %s -> %s",
            ticket.start,
            ticket.end
        )
        textDate.text = String.format("Ngày : %s", Utils.parseLocalDateFormat(ticket.date))
        textPickLocation.text =
            String.format("Điểm đón : %s", ticket.pickLocation.detailLocation)
        textDestinationLocation.text =
            String.format("Điểm trả : %s", ticket.destination.detailLocation)
        textPositionCode.text = String.format("Mã ghế : %s", ticket.positionCode)
    }
}