package com.example.coachticketbooking.screen.choose_location

import android.os.Bundle
import com.example.coachticketbooking.R
import com.example.coachticketbooking.adapter.LocationPagerAdapter
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.base.view.disable
import com.example.coachticketbooking.base.view.enable
import com.example.coachticketbooking.model.UserData
import com.example.coachticketbooking.screen.ticket.PreviewTicketFragment
import com.example.coachticketbooking.utils.Utils
import kotlinx.android.synthetic.main.choose_location_fragment.*

class ChooseLocationFragment : BaseFragment() {

    companion object {
        const val CHOOSE_LOCATION_FRAGMENT_TAG = "ChooseLocationFragment"
        fun newInstance() = ChooseLocationFragment()
    }

    private lateinit var viewModel: ChooseLocationViewModel
    private lateinit var pagerAdapter: LocationPagerAdapter
    override fun getLayoutId(): Int = R.layout.choose_location_fragment

    override fun initView() {
        pagerAdapter = LocationPagerAdapter(childFragmentManager)
        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
        toolbar.title = getString(R.string.chooseLocationTitile)
    }

    override fun initData(bundle: Bundle?) {
        textPositionCode.text = UserData.position.map { it.positionCode }.toString()
        textSum.text = String.format("%sd", Utils.getCurrencyFormat(UserData.price))
        checkEnableContinueButton()
    }

    override fun initObserver() {
    }

    override fun initListener() {
        btnContinue.setOnClickListener {
            val previewTicketFragment = PreviewTicketFragment.newInstance()
            pushFragment(previewTicketFragment, withAnimation = true, tag = "PreviewTicket")
        }

        toolbar.setNavigationOnClickListener {
            popBackStack()
        }

        pagerAdapter.onPositionSelected = {
            checkEnableContinueButton()
        }
    }

    private fun checkEnableContinueButton() {
        if (UserData.destination != null && UserData.pickLocation != null) btnContinue.enable()
        else btnContinue.disable()
    }
}
