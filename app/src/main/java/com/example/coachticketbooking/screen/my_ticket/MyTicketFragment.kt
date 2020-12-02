package com.example.coachticketbooking.screen.my_ticket

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coachticketbooking.R
import com.example.coachticketbooking.adapter.TicketAdapter
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.screen.MainActivity
import com.example.coachticketbooking.screen.ticket_detail.TicketDetailFragment
import com.example.coachticketbooking.utils.SharePreferenceUtils
import kotlinx.android.synthetic.main.my_ticket_fragment.*

class MyTicketFragment : BaseFragment() {

    companion object {
        fun newInstance() = MyTicketFragment()
    }

    private lateinit var mMyTicketViewModel: MyTicketViewModel
    private val mMyTicketAdapter = TicketAdapter()

    override fun getLayoutId(): Int = R.layout.my_ticket_fragment

    override fun initView() {
        recyclerTickets?.apply {
            adapter = mMyTicketAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun initData(bundle: Bundle?) {
        mMyTicketViewModel = ViewModelProvider(this).get(MyTicketViewModel::class.java)
        context?.let {
            val user = SharePreferenceUtils.getLocalUserInformation(it)
            user?.apply {
                mMyTicketViewModel.getMyTickets(phoneNumber)
            }
        }
    }

    override fun initObserver() {
        mMyTicketViewModel.myTicketsLiveData.observe(this, {
            mMyTicketAdapter.setData(it)
        })
    }

    override fun initListener() {
        mMyTicketAdapter.setOnTicketClickListener {
            val intent = Intent(rootActivity, MainActivity::class.java)
            intent.putExtra(TicketDetailFragment.KEY_TICKET_ID, it)
            intent.putExtra(MainActivity.KEY_MODE, MainActivity.MODE_MY_TICKETS)
            startActivity(intent)
        }
    }
}