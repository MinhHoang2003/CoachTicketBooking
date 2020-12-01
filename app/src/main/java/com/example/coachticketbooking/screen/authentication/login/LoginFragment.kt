package com.example.coachticketbooking.screen.authentication.login

import android.app.Activity
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.screen.authentication.register.RegisterFragment
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.login_fragment.*

class LoginFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var mLoginViewModel: LoginViewModel
    override fun getLayoutId(): Int = R.layout.login_fragment

    override fun initView() {
        //Nothing
    }

    override fun initData(bundle: Bundle?) {
        context?.let {
            mLoginViewModel = ViewModelProvider(
                this,
                LoginViewModelFactory(it)
            ).get(LoginViewModel::class.java)
        }
    }

    override fun initObserver() {
        mLoginViewModel.mLoading.observe(this, { isLoading ->
            if (isLoading) {
                showLoading()
            } else hideLoading()
        })
        mLoginViewModel.loginResultLiveData.observe(this, { result ->
            if (result) {
                context?.let { context ->
                    Toasty.success(
                        context,
                        "Đăng nhập thành công",
                        Toast.LENGTH_SHORT,
                        true
                    ).show()
                }

                rootActivity?.setResult(Activity.RESULT_OK)
                rootActivity?.finish()
            }
        })
    }

    override fun initListener() {
        btnLogin.setOnClickListener(this)
        textSignUp.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnLogin -> {
                val phoneNumber: String = edtPhoneNumber.text.toString()
                val password: String = edtPassword.text.toString()
                mLoginViewModel.login(phoneNumber, password)
            }

            textSignUp -> {
                val registerFragment = RegisterFragment.newInstance()
                replaceFragment(registerFragment, withAnimation = true, tag = "Register")
            }
        }
    }

}