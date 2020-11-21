package com.example.coachticketbooking.screen.choose_position

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coachticketbooking.R
import com.example.coachticketbooking.adapter.PositionAdapter
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.base.DebugLog
import com.example.coachticketbooking.model.UserData
import com.example.coachticketbooking.repository.chooose_position.ChoosePositionViewModelFactory
import com.example.coachticketbooking.screen.choose_location.ChooseLocationFragment
import kotlinx.android.synthetic.main.fragment_choose_position.*


class ChoosePositionFragment : BaseFragment() {

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
            toolbar.setNavigationIcon(R.drawable.icon_arrow_left)
            toolbar.title = "Chọn ghế ngồi"
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
        mPositionAdapter.onSelectedChangeListener = {
            if (it.isNotEmpty()) {
                UserData.position.clear()
                UserData.position.addAll(mPositionAdapter.selectedPositions)
                textPositionCode.text = it.toString().substring(1, it.toString().length - 1)
            } else {
                textPositionCode.text = "__"
            }
        }
        btnContinue.setOnClickListener {
            val chooseLocationFragment = ChooseLocationFragment.newInstance()
            pushFragment(chooseLocationFragment, withAnimation = true)
        }
    }


}