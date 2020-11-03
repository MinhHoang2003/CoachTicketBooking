package com.example.coachticketbooking.screen.home

import android.os.Bundle
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val homeFragment = HomeFragment.newInstance()
        pushFragment(homeFragment, false, null, R.id.fragment_container, null)
    }
}