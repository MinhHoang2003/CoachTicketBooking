package com.example.coachticketbooking.utils

import android.content.Context
import android.widget.Toast
import es.dmoral.toasty.Toasty

object ToastyUtils {
    fun showError(context : Context?, message : String) {
        context?.apply {
            Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()
        }
    }

    fun showSuccess( context: Context?, message: String) {
        context?.apply {
            Toasty.success(this, message, Toast.LENGTH_SHORT, true).show()
        }
    }
}