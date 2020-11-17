package com.example.coachticketbooking.screen.choose_position.choose_position_infor

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coachticketbooking.R

class ChoosePickLocationFragment : Fragment() {

    companion object {
        fun newInstance() = ChoosePickLocationFragment()
    }

    private lateinit var viewModel: ChoosePickLocationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choose_pick_location_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChoosePickLocationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}