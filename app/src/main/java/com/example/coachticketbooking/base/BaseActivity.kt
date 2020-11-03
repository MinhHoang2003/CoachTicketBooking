package com.example.coachticketbooking.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.coachticketbooking.R
import java.lang.IllegalArgumentException

abstract class BaseActivity : AppCompatActivity(), BaseTransitionFragment {
    companion object {
        private const val MIN_BACK_STACK_ANIMATION = 4
        private const val MIN_NON_BACK_STACK_ANIMATION = 4
    }

    private val mDialogLoading: DialogLoading by lazy { DialogLoading(this) }

    fun hideSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let { currentFocus ->
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

    fun showSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (!imm.isAcceptingText) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    fun showLoading() {
        if (!mDialogLoading.isShowing) mDialogLoading.showDialog()
    }

    fun hideLoading() {
        if (mDialogLoading.isShowing) mDialogLoading.hideDialog()
    }

    override fun popBackStack() {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack()
        } else {
            finish()
        }
    }

    override fun popBackStack(tag: String) {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack(tag, 0)
        } else {
            finish()
        }
    }

    override fun pushFragment(
        fragment: Fragment,
        withAnimation: Boolean,
        animation: IntArray?,
        containerView: Int,
        tag: String?
    ) {
        showFragment(fragment, true, withAnimation, animation, containerView, tag)
    }

    override fun addFragment(
        fragment: Fragment,
        withAnimation: Boolean,
        animation: IntArray?,
        containerView: Int,
        tag: String?
    ) {
        supportFragmentManager.beginTransaction().apply {
            if (withAnimation) {
                val anim = animation ?: intArrayOf(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )

                if (anim.size < MIN_BACK_STACK_ANIMATION) throw IllegalArgumentException("You must provide at least $MIN_BACK_STACK_ANIMATION animations")
                setCustomAnimations(anim[0], anim[1], anim[2], anim[3])
            }

            addToBackStack(fragment.tag)
            add(containerView, fragment)
            commit()
        }
    }

    override fun replaceFragment(
        fragment: Fragment,
        withAnimation: Boolean,
        animation: IntArray?,
        containerView: Int,
        tag: String?
    ) {
        showFragment(fragment, false, withAnimation, animation, containerView, tag)
    }

    override fun removeFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
    }

    private fun showFragment(
        fragment: Fragment,
        hasAddBackStack: Boolean,
        withAnimation: Boolean,
        animation: IntArray?,
        containerView: Int,
        tag: String?
    ) {
        supportFragmentManager.beginTransaction().apply {
            if (withAnimation) {
                val anim = animation ?: intArrayOf(
                    R.anim.slide_in_right,
                    R.anim.slide_out_left,
                    R.anim.slide_in_left,
                    R.anim.slide_out_right
                )

                if (hasAddBackStack) {
                    if (anim.size < MIN_BACK_STACK_ANIMATION) throw IllegalArgumentException("You must provide at least $MIN_BACK_STACK_ANIMATION animations")
                    setCustomAnimations(anim[0], anim[1], anim[2], anim[3])
                } else {
                    if (anim.size < MIN_NON_BACK_STACK_ANIMATION) throw IllegalArgumentException("You must provide at least $MIN_BACK_STACK_ANIMATION animations")
                    setCustomAnimations(anim[0], anim[1])
                }
            }

            if (hasAddBackStack) {
                addToBackStack(tag)
            }
            replace(containerView, fragment, tag)
            commitAllowingStateLoss()
        }
    }

}
