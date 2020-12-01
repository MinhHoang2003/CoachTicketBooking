package com.example.coachticketbooking.screen.choose_position

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ChoosePositionViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChoosePositionViewModel::class.java)) {
            return ChoosePositionViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}