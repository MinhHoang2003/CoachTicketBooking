package com.example.coachticketbooking.screen.ticket

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.base.DebugLog
import com.example.coachticketbooking.model.UserData
import com.example.coachticketbooking.utils.Constants
import com.example.coachticketbooking.utils.Utils
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import kotlinx.android.synthetic.main.preview_ticket_fragment.*
import java.math.BigDecimal

class PreviewTicketFragment : BaseFragment() {

    private val payPalConfig: PayPalConfiguration =
        PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Constants.PAYPAL_CLIENT_ID)

    companion object {
        const val PAY_PAL_REQUEST_CODE = 1111
        fun newInstance() = PreviewTicketFragment()
    }

    private lateinit var viewModel: PreviewTicketViewModel
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
    }

    override fun initObserver() {
    }

    override fun initListener() {
        btnPay.setOnClickListener {
            processPayment(
                UserData.price,
                String.format("%s -> %s", UserData.route?.startAddress, UserData.route?.endAddress)
            )
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
            BigDecimal(22),
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PAY_PAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DebugLog.e("Hoang on result ok")
            } else {
                DebugLog.e("Hoang on result err")
            }
        }
    }
}
