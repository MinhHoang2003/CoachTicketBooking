package com.example.coachticketbooking.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.coachticketbooking.R
import com.example.coachticketbooking.dialog.DialogLoading
import java.lang.IllegalArgumentException

abstract class BaseActivity : AppCompatActivity(), BaseView, BaseTransitionFragment {
    companion object {
        private const val MIN_BACK_STACK_ANIMATION = 4
        private const val MIN_NON_BACK_STACK_ANIMATION = 4
    }

    private val mDialogLoading: DialogLoading by lazy { DialogLoading(this) }

    override fun hideSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        currentFocus?.let { currentFocus ->
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

    override fun showSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (!imm.isAcceptingText) {
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }

    override fun showLoading() {
        if (!mDialogLoading.isShowing) mDialogLoading.showDialog()
    }

    override fun hideLoading() {
        if (mDialogLoading.isShowing) mDialogLoading.hideDialog()
    }

    override fun popBackStack() {
        val fm = supportFragmentManager
        if (fm.backStackEntryCount > 1) {
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
        tag: String?
    ) {
        showFragment(fragment, true, withAnimation, animation, tag)
    }

    override fun addFragment(
        fragment: Fragment,
        withAnimation: Boolean,
        animation: IntArray?,
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
            add(getContainerFragmentView(), fragment)
            commit()
        }
    }

    override fun replaceFragment(
        fragment: Fragment,
        withAnimation: Boolean,
        animation: IntArray?,
        tag: String?
    ) {
        showFragment(fragment, false, withAnimation, animation, tag)
    }

    override fun removeFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack()
    }

    private fun showFragment(
        fragment: Fragment,
        hasAddBackStack: Boolean,
        withAnimation: Boolean,
        animation: IntArray?,
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
            replace(getContainerFragmentView(), fragment, tag)
            commitAllowingStateLoss()
        }
    }

    abstract fun getContainerFragmentView(): Int

}
