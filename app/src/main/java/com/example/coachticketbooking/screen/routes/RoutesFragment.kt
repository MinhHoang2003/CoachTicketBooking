package com.example.coachticketbooking.screen.routes

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachticketbooking.R
import com.example.coachticketbooking.adapter.RouteAdapter
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.model.RouteSearchPattern
import com.example.coachticketbooking.model.UserData
import com.example.coachticketbooking.screen.choose_position.ChoosePositionFragment
import com.example.coachticketbooking.utils.Constants
import com.example.coachticketbooking.utils.Utils
import kotlinx.android.synthetic.main.fragment_routes.*

class RoutesFragment : BaseFragment() {

    companion object {
        const val KEY_SEARCH_QUERY = "search_query"

        fun newInstance(routeSearchPattern: RouteSearchPattern) =
            RoutesFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_SEARCH_QUERY, routeSearchPattern)
                }
            }
    }

    lateinit var mRoutesViewModel: RoutesViewModel
    private var search: RouteSearchPattern? = null
    private val routesAdapter = RouteAdapter()


    override fun initData(bundle: Bundle?) {
        mRoutesViewModel = ViewModelProvider(this).get(RoutesViewModel::class.java)
        bundle?.apply {
            search = getParcelable(KEY_SEARCH_QUERY)
            remove(KEY_SEARCH_QUERY)
            arguments = null
        }

        search?.apply {
            textRouteTitle.text = String.format("%s --> %s", pickLocation, destination)
            textDate.text = date
            mRoutesViewModel.searchRoutes(pickLocation, destination, Utils.getServerDateFormat(date))
        }
    }

    override fun initView() {
        recyclerRoutes.adapter = routesAdapter
        recyclerRoutes.layoutManager = LinearLayoutManager(context)
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
    }

    override fun initListener() {
        routesAdapter.onItemClick = { selectedRoute ->
            // Show fragment choose position.
            UserData.apply {
                resetData()
                route = selectedRoute
            }
            val choosePositionFragment = ChoosePositionFragment.newInstance(
                selectedRoute.id.toString(),
                search?.date ?: Constants.EMPTY_STRING
            )
            pushFragment(choosePositionFragment, withAnimation = true, tag = "Choose Position")
        }
        toolbar.setNavigationOnClickListener {
            popBackStack()
        }

        imgNext.setOnClickListener {
            search?.apply {
                date = Utils.increaseNextDay(date)
                UserData.date = date
                textDate.text = date
                mRoutesViewModel.searchRoutes(pickLocation, destination, Utils.getServerDateFormat(date))
            }
        }

        imgPrevious.setOnClickListener {
            search?.apply {
                date = Utils.decreasePreviousDay(date)
                UserData.date = date
                textDate.text = date
                mRoutesViewModel.searchRoutes(pickLocation, destination, Utils.getServerDateFormat(date))
            }
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_routes


    override fun initObserver() {
        mRoutesViewModel.mLoading.observe(this, { isLoading ->
            if (isLoading) {
                showLoading()
            } else hideLoading()
        })

        mRoutesViewModel.mError.observe(this, {
            // TODO show error dialog.
        })

        mRoutesViewModel.routesLiveData.observe(this, {
            routesAdapter.setData(it)
        })
    }
}