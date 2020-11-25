package com.example.coachticketbooking.screen

import android.content.Intent
import android.os.Bundle
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.BaseActivity
import com.example.coachticketbooking.model.RouteSearchPattern
import com.example.coachticketbooking.screen.home.HomeActivity
import com.example.coachticketbooking.screen.home.HomeFragment
import com.example.coachticketbooking.screen.routes.RoutesFragment
import com.example.coachticketbooking.utils.Constants

class MainActivity : BaseActivity() {

    override fun getContainerFragmentView(): Int = R.id.main_fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val search = RouteSearchPattern(
            intent.getStringExtra(HomeFragment.KEY_PICK_LOCATION) ?: Constants.EMPTY_STRING,
            intent.getStringExtra(HomeFragment.KEY_DESTINATION) ?: Constants.EMPTY_STRING,
            intent.getStringExtra(HomeFragment.KEY_DATE) ?: Constants.EMPTY_STRING
        )
        val routesFragment = RoutesFragment.newInstance(search)
        pushFragment(routesFragment, withAnimation = true)
    }

    override fun onBackPressed() {
        popBackStack()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.findFragmentByTag("PreviewTicket")
        fragment?.onActivityResult(requestCode, resultCode, data)
    }

}