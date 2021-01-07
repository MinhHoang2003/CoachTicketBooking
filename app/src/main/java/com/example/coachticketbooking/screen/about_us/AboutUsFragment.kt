package com.example.coachticketbooking.screen.about_us

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.utils.Utils
import kotlinx.android.synthetic.main.fragment_about_us.*


class AboutUsFragment : BaseFragment(), View.OnClickListener {

    companion object {
        @JvmStatic
        fun newInstance() = AboutUsFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_about_us

    override fun initView() {
        textPhone.text = Utils.PHONE_NUMBER
        textEmail.text = Utils.EMAIL
    }

    override fun initData(bundle: Bundle?) {
    }

    override fun initObserver() {
    }

    override fun initListener() {
        textPhone.setOnClickListener(this)
        imgPhone.setOnClickListener(this)
        textEmail.setOnClickListener(this)
        imgEmail.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            textPhone, imgPhone -> {
                val number: Uri = Uri.parse("tel:${Utils.PHONE_NUMBER}")
                val callIntent = Intent(Intent.ACTION_DIAL, number)
                startActivity(callIntent)
            }

            textEmail, imgEmail -> {
                val uri = Uri.parse("mailto:${Utils.EMAIL}")
                    .buildUpon()
                    .build()

                val emailIntent = Intent(Intent.ACTION_SENDTO, uri)
                startActivity(Intent.createChooser(emailIntent, "Góp ý"))
            }
        }
    }
}