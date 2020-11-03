package com.example.coachticketbooking.base

import androidx.fragment.app.Fragment

interface BaseTransitionFragment {
    fun popBackStack()
    fun popBackStack(tag: String)
    fun pushFragment(
        fragment: Fragment,
        withAnimation: Boolean = false,
        animation: IntArray? = null,
        containerView: Int,
        tag: String? = null
    )

    fun replaceFragment(
        fragment: Fragment,
        withAnimation: Boolean = false,
        animation: IntArray? = null,
        containerView: Int,
        tag: String? = null
    )

    fun addFragment(
        fragment: Fragment,
        withAnimation: Boolean = false,
        animation: IntArray? = null,
        containerView: Int,
        tag: String? = null
    )

    fun removeFragment(fragment: Fragment)
}