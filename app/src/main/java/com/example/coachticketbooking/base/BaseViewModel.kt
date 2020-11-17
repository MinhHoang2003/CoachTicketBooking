package com.example.coachticketbooking.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coachticketbooking.utils.Constants
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    var disposable = CompositeDisposable()
    val mLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val mError: MutableLiveData<String> = MutableLiveData(Constants.EMPTY_STRING)

    override fun onCleared() {
        disposable.clear()
        disposable.dispose()
        super.onCleared()
    }

    fun showLoading(isShow: Boolean) {
        mLoading.value = isShow
    }

    fun onError(err: String) {
        mError.value = err
    }

}