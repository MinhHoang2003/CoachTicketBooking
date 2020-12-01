package com.example.coachticketbooking.screen.authentication

import android.os.Bundle
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.BaseActivity
import com.example.coachticketbooking.screen.authentication.login.LoginFragment

class AuthenticationActivity : BaseActivity() {

    override fun getContainerFragmentView(): Int = R.id.authenticationFragmentContainer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)
        initView()
    }

    private fun initView() {
        val loginFragment = LoginFragment.newInstance()
        pushFragment(loginFragment, withAnimation = false, tag = "Login")
    }

    override fun onBackPressed() {
        popBackStack()
    }
}