package com.example.coachticketbooking.networking

import com.example.coachticketbooking.utils.Constants
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(getHttpClient().build())

    private fun getHttpClient() : OkHttpClient.Builder {
        val list = arrayListOf(ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT)
        val logging = HttpLoggingInterceptor()
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        httpClient.readTimeout(Constants.READ_TIME_OUT, TimeUnit.MILLISECONDS)
        httpClient.connectTimeout(Constants.CONNECTION_TIME_OUT, TimeUnit.MILLISECONDS)
        httpClient.connectionSpecs(list)
        return httpClient
    }

    fun getClient(): Retrofit = builder.build()

    fun getAPIService(): APIService = getClient().create(APIService::class.java)
}