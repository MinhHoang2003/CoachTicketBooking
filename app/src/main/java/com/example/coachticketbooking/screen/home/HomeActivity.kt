package com.example.coachticketbooking.screen.home

import android.os.Bundle
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.BaseActivity

class HomeActivity : BaseActivity() {

    override fun getContainerFragmentView(): Int = R.id.fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val homeFragment = HomeFragment.newInstance()
        pushFragment(homeFragment, false, null, null)
    }
}