package com.example.coachticketbooking.screen.authentication.register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.coachticketbooking.base.BaseViewModel
import com.example.coachticketbooking.base.addToCompositeDisposable
import com.example.coachticketbooking.base.applyScheduler
import com.example.coachticketbooking.model.User
import com.example.coachticketbooking.networking.RetrofitClient
import com.example.coachticketbooking.repository.user.UserRepository
import com.example.coachticketbooking.utils.SharePreferenceUtils

class RegisterViewModel(private val context: Context) : BaseViewModel() {

    private val mUserRepository by lazy {
        UserRepository(RetrofitClient.getAPIService())
    }

    val registerResultLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun register(user: User) {
        mUserRepository.register(user)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe({
                SharePreferenceUtils.saveUserData(context, user)
                registerResultLiveData.value = true
            }, {
                mError.value = it.message
            }).addToCompositeDisposable(disposable)
    }
}
