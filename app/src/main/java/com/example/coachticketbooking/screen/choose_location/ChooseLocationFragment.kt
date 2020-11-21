package com.example.coachticketbooking.screen.choose_location

import android.os.Bundle
import com.example.coachticketbooking.R
import com.example.coachticketbooking.adapter.LocationPagerAdapter
import com.example.coachticketbooking.base.BaseFragment
import kotlinx.android.synthetic.main.choose_location_fragment.*

class ChooseLocationFragment : BaseFragment() {

    companion object {
        fun newInstance() = ChooseLocationFragment()
    }

    private lateinit var viewModel: ChooseLocationViewModel
    override fun getLayoutId(): Int = R.layout.choose_location_fragment

    override fun initView() {
        val pagerAdapter = LocationPagerAdapter(childFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
        toolbar.title = "Chọn điểm lên xuống xe"
    }

    override fun initData(bundle: Bundle?) {
        // Do nothing
    }

    override fun initObserver() {
    }

    override fun initListener() {
        btnContinue.setOnClickListener {

        }
    }
}
