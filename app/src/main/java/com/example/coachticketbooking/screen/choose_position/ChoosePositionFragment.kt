package com.example.coachticketbooking.screen.choose_position

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coachticketbooking.R
import com.example.coachticketbooking.adapter.PositionAdapter
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.base.view.disable
import com.example.coachticketbooking.base.view.enable
import com.example.coachticketbooking.base.view.invisible
import com.example.coachticketbooking.base.view.visible
import com.example.coachticketbooking.model.UserData
import com.example.coachticketbooking.screen.choose_location.ChooseLocationFragment
import com.example.coachticketbooking.screen.map.MapsActivity
import com.example.coachticketbooking.utils.Constants
import com.example.coachticketbooking.utils.Utils
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_choose_position.*


class ChoosePositionFragment : BaseFragment() {

    private lateinit var mPositionAdapter: PositionAdapter
    lateinit var mChoosePositionViewModel: ChoosePositionViewModel
    lateinit var mChoosePositionViewModelFactory: ChoosePositionViewModelFactory
    private var mCurrentFloor : Int = 1
    private var mCurrentNumberPosition = Constants.COACH_29_POSITION

    private lateinit var toast: Toast

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
            toolbar.title = getString(R.string.choosePositionTitle)
            mPositionAdapter = PositionAdapter(it)
            recyclerPositions.apply {
                adapter = mPositionAdapter
                layoutManager = GridLayoutManager(context, 5)
            }
        }
    }

    override fun initData(bundle: Bundle?) {
        context?.let { context ->
            toast = Toasty.warning(context, "Bạn chỉ có thể chọn tối đa 4 chỗ ngồi", Toast.LENGTH_SHORT, true)
            mChoosePositionViewModelFactory = ChoosePositionViewModelFactory(context)
            mChoosePositionViewModel = ViewModelProvider(
                this,
                mChoosePositionViewModelFactory
            ).get(ChoosePositionViewModel::class.java)

            mCurrentFloor = 1
            mCurrentNumberPosition = UserData.route?.numberPosition ?: Constants.COACH_46_POSITION
            mChoosePositionViewModel.getPositions(
                UserData.route?.id ?: Constants.NON_ID_DEFAULT,
                UserData.getDateConverted(),
                mCurrentFloor,
                mCurrentNumberPosition
            )

            checkShowFloorImage()
        }
    }

    private fun checkShowFloorImage() {
        if (mCurrentNumberPosition == Constants.COACH_46_POSITION) {
            if (mCurrentFloor == 1) {
                imgNext.visible()
                imgPrevious.invisible()
            } else {
                imgNext.invisible()
                imgPrevious.visible()
            }
        } else {
            imgNext.invisible()
            imgPrevious.invisible()
        }
    }

    override fun initObserver() {
        mChoosePositionViewModel.positionsLiveData.observe(this, {
            mPositionAdapter.setData(it)
            mPositionAdapter.addSelectionPosition(UserData.position)
        })
        mChoosePositionViewModel.mLoading.observe(this, {
            if(it) showLoading() else hideLoading()
        })
    }

    override fun initListener() {
        toolbar.setNavigationOnClickListener {
            popBackStack()
        }

        mPositionAdapter.onMaxPositionReach = {
           if(toast.view?.isShown == false) {
               toast.show()
           }
        }

        mPositionAdapter.onSelectedChangeListener = {
            if (it.isNotEmpty()) {
                UserData.position.clear()
                UserData.position.addAll(mPositionAdapter.selectedPositions)
                textPositionCode.text = it.toString().substring(1, it.toString().length - 1)
                UserData.apply {
                    price = (route?.price ?: 0) * (position.size)
                    textSum.text = String.format("%sd", Utils.getCurrencyFormat(price))
                    btnContinue.enable()
                }
            } else {
                textPositionCode.text = getString(R.string.choosePositionUnSelectedTitle)
                textSum.text = String.format("%sd", 0)
                btnContinue.disable()
            }
        }
        btnContinue.setOnClickListener {
            val chooseLocationFragment = ChooseLocationFragment.newInstance()
            pushFragment(chooseLocationFragment, withAnimation = true)
        }

        imgNext.setOnClickListener {
            mCurrentFloor = 2
            checkShowFloorImage()
            mChoosePositionViewModel.getPositions(
                UserData.route?.id ?: Constants.NON_ID_DEFAULT,
                UserData.getDateConverted(),
                mCurrentFloor,
                mCurrentNumberPosition
            )
        }

        imgPrevious.setOnClickListener {
            mCurrentFloor = 1
            checkShowFloorImage()
            mChoosePositionViewModel.getPositions(
                UserData.route?.id ?: Constants.NON_ID_DEFAULT,
                UserData.getDateConverted(),
                mCurrentFloor,
                mCurrentNumberPosition
            )
        }

        imgMap.setOnClickListener {
            val intent = Intent(rootActivity, MapsActivity::class.java)
            val currentId = UserData.route?.id ?: -1
            intent.putExtra("id", currentId)
            startActivity(intent)
        }
    }


}