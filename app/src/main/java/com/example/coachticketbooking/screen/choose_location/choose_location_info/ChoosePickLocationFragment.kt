package com.example.coachticketbooking.screen.choose_location.choose_location_info

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachticketbooking.R
import com.example.coachticketbooking.adapter.LocationAdapter
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.model.User
import com.example.coachticketbooking.model.UserData
import com.example.coachticketbooking.screen.routes.RoutesViewModel
import com.example.coachticketbooking.utils.Constants
import kotlinx.android.synthetic.main.choose_location_fragment.*
import kotlinx.android.synthetic.main.choose_pick_location_fragment.*

class ChoosePickLocationFragment : BaseFragment() {

    companion object {
        const val KEY_POSITION_CODE = "POSITION_CODE"
        const val KEY_PRICE = "PRICE"
        const val KEY_MODE = "MODE"
        const val KEY_ROUTE_ID = "ROUTE_ID"

        // Mode
        const val MODE_PICK_LOCATION = 1
        const val MODE_DESTINATION = 2
        fun newInstance(
            mode: Int,
        ): ChoosePickLocationFragment {
            val fragment = ChoosePickLocationFragment()
            val bundle = Bundle()
            bundle.apply {
                putInt(KEY_MODE, mode)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mCurrentMode = MODE_PICK_LOCATION
    private lateinit var mChoosePickLocationViewModel: ChoosePickLocationViewModel
    private val mLocationAdapter = LocationAdapter()
    override fun getLayoutId(): Int = R.layout.choose_pick_location_fragment

    var onPickLocationSelected: (() -> Unit)? = null
    var onDestinationLocationSelected: (() -> Unit)? = null


    override fun initView() {
        recyclerLocationPicker.apply {
            adapter = mLocationAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun initData(bundle: Bundle?) {
        val routeId = UserData.route?.id ?: -1
        bundle?.apply {
            mCurrentMode = getInt(KEY_MODE, MODE_PICK_LOCATION)
        }
        mChoosePickLocationViewModel = ViewModelProvider(this).get(
            ChoosePickLocationViewModel::class.java
        )

        if (mCurrentMode == MODE_PICK_LOCATION) {
            mChoosePickLocationViewModel.getPickLocationOfRoute(routeId)
        } else {
            mChoosePickLocationViewModel.getDestinationLocationOfRoute(routeId)
        }
    }

    override fun initObserver() {
        mChoosePickLocationViewModel.mLoading.observe(this, {
            if(it) showLoading() else hideLoading()
        })
        if (mCurrentMode == MODE_PICK_LOCATION) {
            mChoosePickLocationViewModel.pickLocationLiveData.observe(this, { pickLocations ->
                mLocationAdapter.setData(pickLocations)
                UserData.pickLocation?.let { mLocationAdapter.setCurrentLocation(it) }
            })
        } else {
            mChoosePickLocationViewModel.destinationLocationLiveData.observe(this, { destinations ->
                mLocationAdapter.setData(destinations)
                UserData.destination?.let {
                    mLocationAdapter.setCurrentLocation(it)
                }
            })
        }
    }

    override fun initListener() {
        mLocationAdapter.onLocationClickListener = {
            if (mCurrentMode == MODE_PICK_LOCATION) {
                UserData.pickLocation = it
                onPickLocationSelected?.invoke()
            } else {
                UserData.destination = it
                onDestinationLocationSelected?.invoke()
            }
        }
    }

}
