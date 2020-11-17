package com.example.coachticketbooking.screen.routes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coachticketbooking.R
import com.example.coachticketbooking.adapter.RouteAdapter
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.base.DebugLog
import com.example.coachticketbooking.model.RouteSearchPattern
import com.example.coachticketbooking.screen.choose_position.ChoosePositionFragment
import com.example.coachticketbooking.screen.home.HomeViewModel
import com.example.coachticketbooking.utils.Constants
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
            DebugLog.d("Hoang $search")
            search?.apply {
                textRouteTitle.text = String.format("%s --> %s", pickLocation, destination)
                textDate.text = date
                mRoutesViewModel.searchRoutes(pickLocation, destination, date)
            }
        }
    }

    override fun initView() {
        recyclerRoutes.adapter = routesAdapter
        recyclerRoutes.layoutManager = LinearLayoutManager(context)
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
    }

    override fun initListener() {
        routesAdapter.onItemClick = {
            // Show fragment choose position.
            val choosePositionFragment = ChoosePositionFragment.newInstance(
                it.toString(),
                search?.date ?: Constants.EMPTY_STRING
            )
            pushFragment(choosePositionFragment, withAnimation = true, tag = "Choose Position")
        }
        toolbar.setNavigationOnClickListener {
            popBackStack()
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