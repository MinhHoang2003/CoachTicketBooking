package com.example.coachticketbooking.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.bumptech.glide.Glide
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.DebugLog
import kotlinx.android.synthetic.main.layout_dialog_preview_qr_ticket.*

class DialogTicketQRPreview(context: Context) : Dialog(context) {

    companion object {
        private const val TAG_SHOW_QR_TICKET = "QRTicket"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_dialog_preview_qr_ticket)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        background.setOnClickListener { hideDialog() }
    }


    fun showDialog(qrBitmap: Bitmap) {
        try {
            if (!isShowing) {
                show()
                Glide.with(context).load(qrBitmap).into(imgQR)
            }
        } catch (e: Exception) {
            DebugLog.e("$TAG_SHOW_QR_TICKET : Error lake window")
        }
    }

    fun hideDialog() = dismiss()
}