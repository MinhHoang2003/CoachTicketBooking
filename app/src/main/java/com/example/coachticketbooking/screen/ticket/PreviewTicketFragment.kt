package com.example.coachticketbooking.screen.ticket

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.base.DebugLog
import com.example.coachticketbooking.dialog.DialogTicketQRPreview
import com.example.coachticketbooking.model.Location
import com.example.coachticketbooking.model.TicketLocalModel
import com.example.coachticketbooking.model.User
import com.example.coachticketbooking.model.UserData
import com.example.coachticketbooking.screen.authentication.AuthenticationActivity
import com.example.coachticketbooking.utils.Constants
import com.example.coachticketbooking.utils.SharePreferenceUtils
import com.example.coachticketbooking.utils.Utils
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.preview_ticket_fragment.*
import net.glxn.qrgen.android.QRCode
import java.math.BigDecimal

class PreviewTicketFragment : BaseFragment() {

    private val payPalConfig: PayPalConfiguration =
        PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Constants.PAYPAL_CLIENT_ID)

    companion object {
        const val PAY_PAL_REQUEST_CODE = 1111
        const val LOGIN_REQUEST_CODE = 1
        fun newInstance() = PreviewTicketFragment()
    }

    private lateinit var mPreviewTicketViewModel: PreviewTicketViewModel
    private var newTicketId : Int = -1
    override fun getLayoutId(): Int = R.layout.preview_ticket_fragment


    override fun initView() {
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
        toolbar.title = getString(R.string.previewTicketScreenTitle)
        textSum.text = String.format("%sd", Utils.getCurrencyFormat(UserData.price))
        textRoute.text = String.format(
            "Tuyến xe: %s -> %s",
            UserData.route?.startAddress,
            UserData.route?.endAddress
        )
        textDate.text = String.format("Ngày : %s", UserData.date)
        textPickLocation.text =
            String.format("Điểm đón : %s", UserData.pickLocation?.detailLocation)
        textDestinationLocation.text =
            String.format("Điểm trả : %s", UserData.destination?.detailLocation)
        textPositionCode.text = String.format("Mã ghế : %s", UserData.getPositionCode())
    }

    override fun initData(bundle: Bundle?) {
        mPreviewTicketViewModel = ViewModelProvider(this).get(PreviewTicketViewModel::class.java)
    }

    override fun initObserver() {
        mPreviewTicketViewModel.ticketIdLiveDate.observe(this, { ticketId ->
            if (ticketId == -1) return@observe
            else {
                newTicketId = ticketId
                processPayment(
                    UserData.price,
                    String.format(
                        "%s -> %s",
                        UserData.route?.startAddress,
                        UserData.route?.endAddress
                    )
                )
            }
        })

        mPreviewTicketViewModel.payTicketResult.observe(this, {
            if (it) showQRPayTicket()
        })

        mPreviewTicketViewModel.mLoading.observe(this, {
            if (it) showLoading() else hideLoading()
        })
    }

    private fun showQRPayTicket() {
        if (newTicketId == -1) {
            showError("Có lỗi xảy ra khi lấy mã QR cho vé xe!!!")
            return
        }
        val qr = QRCode.from(newTicketId.toString()).bitmap()
        context?.let {
            val dialogPreviewTicket = DialogTicketQRPreview(it)
            dialogPreviewTicket.showDialog(qr)
            dialogPreviewTicket.setOnDismissListener {
                rootActivity?.finish()
            }
            Toasty.success(
                it,
                getString(R.string.message_ticket_has_paid),
                Toast.LENGTH_SHORT,
                true
            ).show()
        }
    }

    override fun initListener() {
        btnPay.setOnClickListener {
            context?.let {
                val localUser = SharePreferenceUtils.getLocalUserInformation(it)
                if (localUser == null) {
                    startActivityForResult(Intent(context, AuthenticationActivity::class.java), LOGIN_REQUEST_CODE)
                } else {
                    requestTicket()
                }
            }
        }
        toolbar.setNavigationOnClickListener {
            popBackStack()
        }
    }

    override fun onDestroy() {
        rootActivity?.startService(Intent(rootActivity, PayPalService::class.java))
        super.onDestroy()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val intent = Intent(rootActivity, PayPalService::class.java)
        //Create service for pay pal payment
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig)
        rootActivity?.startService(intent)
    }

    private fun processPayment(price: Int, title: String) {
        val payPalPayment = PayPalPayment(
            BigDecimal(price),
            "USD",
            title,
            PayPalPayment.PAYMENT_INTENT_SALE
        )
        val intent = Intent(rootActivity, PaymentActivity::class.java)
        intent.apply {
            putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig)
            putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment)
        }
        startActivityForResult(intent, PAY_PAL_REQUEST_CODE)
    }

    private fun requestTicket() {
        context?.let { context ->
            SharePreferenceUtils.getLocalUserInformation(context)?.apply {
                mPreviewTicketViewModel.createTicket(
                    TicketLocalModel(
                        UserData.route?.id ?: -1,
                        UserData.getDateConverted(),
                        UserData.price,
                        0,
                        UserData.pickLocation?.id ?: -1,
                        UserData.destination?.id ?: -1,
                        UserData.position.map { it.positionCode },
                        phoneNumber
                    )
                )
            }

        }
    }

    private fun showError(message : String) {
        context?.let {
            Toasty.error(
                it,
                message,
                Toast.LENGTH_SHORT,
                true
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PAY_PAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (newTicketId != -1) {
                    mPreviewTicketViewModel.payTicket(newTicketId)
                } else showError("Lỗi xảy ra khi thanh toán, vui lòng thử lại")
            } else {
                showError("Lỗi xảy ra khi thanh toán, vui lòng thử lại")
            }
        } else if (requestCode == LOGIN_REQUEST_CODE) {
            processPayment(
                UserData.price,
                String.format(
                    "%s -> %s",
                    UserData.route?.startAddress,
                    UserData.route?.endAddress
                )
            )
        }
    }
}
