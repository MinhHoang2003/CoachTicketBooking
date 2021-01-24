package com.example.coachticketbooking.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.coachticketbooking.screen.choose_location.choose_location_info.ChoosePickLocationFragment

class LocationPagerAdapter(fmn: FragmentManager) :
    FragmentStatePagerAdapter(fmn, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var onPositionSelected : (() -> Unit)? = null

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        val fragment =  when (position) {
            0 -> ChoosePickLocationFragment.newInstance(ChoosePickLocationFragment.MODE_PICK_LOCATION)
            else -> ChoosePickLocationFragment.newInstance(ChoosePickLocationFragment.MODE_DESTINATION)
        }

        if (position == 0) {
            fragment.onPickLocationSelected = onPositionSelected
        } else {
            fragment.onDestinationLocationSelected = onPositionSelected
        }

        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Điểm lên"
            else -> "Điểm xuống"
        }
    }

}