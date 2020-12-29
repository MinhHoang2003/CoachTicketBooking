package com.example.coachticketbooking.screen.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.example.coachticketbooking.R
import com.example.coachticketbooking.base.BaseActivity
import com.example.coachticketbooking.base.BaseFragment
import com.example.coachticketbooking.base.view.invisible
import com.example.coachticketbooking.base.view.visible
import com.example.coachticketbooking.screen.authentication.AuthenticationActivity
import com.example.coachticketbooking.screen.my_ticket.MyTicketFragment
import com.example.coachticketbooking.utils.SharePreferenceUtils
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_header.view.*

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {

    companion object {
        const val TAG_HOME = "Home"
        const val TAG_MY_ROUTES = "MyRoutes"
        const val TAG_MY_TICKETS = "MyTickets"
        const val TAG_NOTIFICATIONS = "Notifications"
    }

    private var mCurrentTag = TAG_HOME
    private var mCurrentFragment: BaseFragment = HomeFragment.newInstance()
    private lateinit var imgAvatar: CircleImageView
    private lateinit var textName: TextView
    private lateinit var textPhoneNumber: TextView

    override fun getContainerFragmentView(): Int = R.id.fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
        showFragment()
    }

    private fun initView() {
        imgNavigationIcon.setOnClickListener {
            initUser()
            drawerLayout.openDrawer(nav_view)
        }
        nav_view.setNavigationItemSelectedListener(this)
        val navigationHeader = nav_view.getHeaderView(0)
        imgAvatar = navigationHeader.imgAvatar
        textName = navigationHeader.textName
        Glide.with(this).load(R.drawable.icon_user).into(imgAvatar)
        initData()

        imgAvatar.setOnClickListener(this)
        textName.setOnClickListener(this)
    }

    private fun initData() {
        initUser()
    }

    private fun initUser() {
        val localUser = SharePreferenceUtils.getLocalUserInformation(context = applicationContext)
        if (localUser != null) {
            textName.text = String.format("Xin chào, %s", localUser.name)
        } else {
            textName.text = "Đăng nhập"
        }
    }

    private fun showFragment() {
        mCurrentFragment = when (mCurrentTag) {
            TAG_HOME -> HomeFragment.newInstance()
            TAG_MY_TICKETS -> MyTicketFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
        pushFragment(mCurrentFragment, false, null, null)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> mCurrentTag = TAG_HOME
            R.id.menu_ticket -> mCurrentTag = TAG_MY_TICKETS
        }
        showFragment()
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onClick(v: View?) {
        when (v) {
            imgAvatar, textName -> {
                val localUser = SharePreferenceUtils.getLocalUserInformation(applicationContext)
                if (localUser == null) {
                    startActivityForResult(Intent(this, AuthenticationActivity::class.java), 1)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) initUser()
        }
    }

}