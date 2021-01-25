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
                    when(err) {
                        VALIDATE_FIELDS -> {
                            mRegisterViewModel?.register(user!!)
                        }

                        ERROR_CODE_PASSWORD_NOT_SAME -> {
                            edtConfirmPassword.error = "Xác nhận mật khẩu không trùng khớp."
                            edtConfirmPassword.requestFocus()
                        }
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
        if (phoneNumber.isBlank()) {
            edtPhoneNumber.error = "Số điện thoại không được để trống"
            edtPhoneNumber.requestFocus()
            return
        }
        val name = edtName.text.toString()

        if (name.isBlank()) {
            edtName.error = "Tên không được để trống"
            edtName.requestFocus()
            return
        }
        val email = edtEmail.text.toString()
        val password = edtPassword.text.toString()
        if (password.isBlank()) {
            edtPassword.error = "Mật khẩu không được để trống"
            edtPassword.requestFocus()
            return
        }
        val confirmPassword = edtConfirmPassword.text.toString()
        if (confirmPassword.isBlank()) {
            edtConfirmPassword.error = "Xác nhận mật khẩu không được để trống"
            edtConfirmPassword.requestFocus()
            return
        }
        val address = edtAddress.text.toString()
        if (password == confirmPassword) {
            result.invoke(User(phoneNumber, name, "", email, password, address, 0), VALIDATE_FIELDS)
            return
        }
        result.invoke(null, ERROR_CODE_PASSWORD_NOT_SAME)
    }
}