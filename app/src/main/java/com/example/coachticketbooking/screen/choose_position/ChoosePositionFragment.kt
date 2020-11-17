package com.example.coachticketbooking.screen.choose_position

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coachticketbooking.R
import com.example.coachticketbooking.adapter.PositionAdapter
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.repository.chooose_position.ChoosePositionViewModelFactory
import kotlinx.android.synthetic.main.fragment_choose_position.*


class ChoosePositionFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var mPositionAdapter: PositionAdapter
    lateinit var mChoosePositionViewModel: ChoosePositionViewModel
    lateinit var mChoosePositionViewModelFactory: ChoosePositionViewModelFactory

    companion object {
        private const val ARG_ID = "ID"
        private const val ARG_DATE = "date"

        @JvmStatic
        fun newInstance(id: String, date: String) =
            ChoosePositionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, id)
                    putString(ARG_DATE, date)
                }
            }
    }

    override fun getLayoutId(): Int = R.layout.fragment_choose_position

    override fun initView() {
        context?.let {
            mPositionAdapter = PositionAdapter(it)
            recyclerPositions.apply {
                adapter = mPositionAdapter
                layoutManager = GridLayoutManager(context, 5)
            }
        }
    }

    override fun initData(bundle: Bundle?) {
        context?.let { context ->
            mChoosePositionViewModelFactory = ChoosePositionViewModelFactory(context)
            mChoosePositionViewModel = ViewModelProvider(
                this,
                mChoosePositionViewModelFactory
            ).get(ChoosePositionViewModel::class.java)
            mChoosePositionViewModel.getPositions(1, "2020-11-15")
        }

    }

    override fun initObserver() {
        mChoosePositionViewModel.positionsLiveData.observe(this, {
            mPositionAdapter.setDate(it)
        })
    }

    override fun initListener() {
        mPositionAdapter.onItemClick = {
            textPositionCode.text = mPositionAdapter.selectedPositions.toString()
        }
    }


}