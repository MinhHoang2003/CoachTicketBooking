package com.example.coachticketbooking.repository.user

import com.example.coachticketbooking.model.User
import com.example.coachticketbooking.model.UserLoginInformation
import com.example.coachticketbooking.networking.APIService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

class UserRepository(private val apiService: APIService) : IUserRepository {

    override fun getUserInformation(phoneNumber: String): Single<User> {
        return apiService.getUserInformation(phoneNumber).flatMap {
            Single.just(it.firstOrNull())
        }
    }

    override fun register(user: User): Completable = apiService.register(user)

    override fun login(username: String, password: String): Completable {
        val userLoginInformation = UserLoginInformation(username, password)
        //TODO need validate in client side
        return apiService.login(userLoginInformation)
    }

}