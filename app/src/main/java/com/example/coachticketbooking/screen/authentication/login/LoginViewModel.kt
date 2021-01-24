package com.example.coachticketbooking.screen.authentication.login

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.coachticketbooking.base.BaseViewModel
import com.example.coachticketbooking.base.addToCompositeDisposable
import com.example.coachticketbooking.base.applyScheduler
import com.example.coachticketbooking.networking.RetrofitClient
import com.example.coachticketbooking.repository.user.UserRepository
import com.example.coachticketbooking.utils.SharePreferenceUtils

class LoginViewModel(private val context: Context) : BaseViewModel() {

    private val userRepository by lazy {
        UserRepository(RetrofitClient.getAPIService())
    }

    val loginResultLiveData: MutableLiveData<Boolean> = MutableLiveData(false)

    fun login(phoneNumber: String, password: String) {
        userRepository.login(phoneNumber, password)
            .applyScheduler()
            .doOnSubscribe { mLoading.value = true }
            .doOnTerminate { mLoading.value = false }
            .subscribe { users, err ->
                if (err == null) {
                    SharePreferenceUtils.saveUserData(context, users)
                    loginResultLiveData.value = true
                } else {
                    mError.value = "Sai thông tin đăng nhập hoặc mật khẩu."
                }
            }.addToCompositeDisposable(disposable)
    }

}