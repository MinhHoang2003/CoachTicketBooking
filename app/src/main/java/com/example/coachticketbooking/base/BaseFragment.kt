package com.example.coachticketbooking.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.coachticketbooking.R

abstract class BaseFragment : Fragment(), BaseView, BaseTransitionFragment {
    val rootActivity: BaseActivity?
        get() = activity as? BaseActivity

    override fun hideLoading() {
        rootActivity?.hideLoading()
    }

    override fun showLoading() {
        rootActivity?.showLoading()
    }

    override fun hideSoftKeyboard() {
        rootActivity?.hideSoftKeyboard()
    }

    override fun showSoftKeyboard() {
        rootActivity?.showSoftKeyboard()
    }

    override fun popBackStack() {
        rootActivity?.popBackStack()
    }

    override fun popBackStack(tag: String) {
        rootActivity?.popBackStack(tag)
    }

    override fun pushFragment(
        fragment: Fragment,
        withAnimation: Boolean,
        animation: IntArray?,
        tag: String?
    ) {
        rootActivity?.pushFragment(fragment, withAnimation, animation, tag)
    }

    override fun replaceFragment(
        fragment: Fragment,
        withAnimation: Boolean,
        animation: IntArray?,
        tag: String?
    ) {
        rootActivity?.replaceFragment(fragment, withAnimation, animation, tag)
    }

    override fun addFragment(
        fragment: Fragment,
        withAnimation: Boolean,
        animation: IntArray?,
        tag: String?
    ) {
        rootActivity?.addFragment(fragment, withAnimation, animation, tag)
    }

    override fun removeFragment(fragment: Fragment) {
        rootActivity?.removeFragment(fragment)
    }

    abstract fun getLayoutId(): Int
    abstract fun initView()
    abstract fun initData(bundle: Bundle?)
    abstract fun initObserver()
    abstract fun initListener()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
        initData(arguments)
        initObserver()
    }

}
