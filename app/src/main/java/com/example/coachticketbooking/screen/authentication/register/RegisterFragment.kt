package com.example.coachticketbooking.screen.authentication.register

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.model.User
import com.example.coachticketbooking.screen.authentication.login.LoginFragment
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.register_fragment.*

class RegisterFragment : BaseFragment(), View.OnClickListener {

    companion object {
        fun newInstance() = RegisterFragment()
        const val ERROR_CODE_PASSWORD_NOT_SAME = 1
        const val VALIDATE_FIELDS = 0
    }

    private var mRegisterViewModel: RegisterViewModel? = null
    override fun getLayoutId(): Int = R.layout.register_fragment

    override fun initView() {
    }

    override fun initData(bundle: Bundle?) {
        context?.let {
            mRegisterViewModel = ViewModelProvider(
                this,
                RegisterViewModelFactory(it)
            ).get(RegisterViewModel::class.java)
        }
    }

    override fun initObserver() {
        mRegisterViewModel?.registerResultLiveData?.observe(this, {
            context?.let { context ->
                Toasty.success(
                    context,
                    getString(R.string.message_success_register),
                    Toast.LENGTH_LONG,
                    true
                ).show()
            }
        })

        mRegisterViewModel?.mLoading?.observe(this, {
            if (it) showLoading() else hideLoading()
        })
    }

    override fun initListener() {
        btnRegister.setOnClickListener(this)
        textSignIn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btnRegister -> {
                getUserData { user, err ->
                    if (err == VALIDATE_FIELDS) {
                        mRegisterViewModel?.register(user!!)
                    }
                }
            }

            textSignIn -> {
                val loginFragment = LoginFragment.newInstance()
                replaceFragment(loginFragment, withAnimation = true, tag = "Login")
            }
        }
    }

    private fun getUserData(result: (User?, Int) -> Unit) {
        val phoneNumber = edtPhoneNumber.text.toString()
        val name = edtName.text.toString()
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        val confirmPassword = edtConfirmPassword.text.toString()
        val address = edtAddress.text.toString()
        if (password == confirmPassword) {
            result.invoke(User(phoneNumber, name, "", email, password, address, 0), VALIDATE_FIELDS)
        }
        result.invoke(null, ERROR_CODE_PASSWORD_NOT_SAME)
    }
}