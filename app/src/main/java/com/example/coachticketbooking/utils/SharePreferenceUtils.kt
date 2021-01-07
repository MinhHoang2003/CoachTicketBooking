package com.example.coachticketbooking.utils

import android.content.Context
import com.example.coachticketbooking.model.User
import com.google.gson.Gson

object SharePreferenceUtils {

    private const val SHARE_PREF_USER = "USER"
    private const val SHARE_KEY_USER = "user"

    fun saveUserData(context: Context, user: User) {
        val sharedPreferences = context.getSharedPreferences(SHARE_PREF_USER, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val userString = Gson().toJson(user)
        editor.putString(SHARE_KEY_USER, userString)
        editor.apply()
    }

    fun getLocalUserInformation(context: Context?): User? {
        if(context == null) return null
        val share = context.getSharedPreferences(SHARE_PREF_USER, Context.MODE_PRIVATE)
        if (share.contains(SHARE_KEY_USER)) {
            return Gson().fromJson(share.getString(SHARE_KEY_USER, ""), User::class.java)
        }
        return null
    }

    fun logout(context: Context?) {
        if (context == null) {
            return
        }
        val editor = context.getSharedPreferences(SHARE_PREF_USER, Context.MODE_PRIVATE).edit()
        editor.remove(SHARE_KEY_USER)
        editor.apply()
    }

}