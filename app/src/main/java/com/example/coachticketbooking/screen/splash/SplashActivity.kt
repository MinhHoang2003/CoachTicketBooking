package com.example.coachticketbooking.screen.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import com.example.coachticketbooking.R
import com.example.coachticketbooking.screen.MainActivity
import com.example.coachticketbooking.screen.home.HomeActivity
import kotlinx.android.synthetic.main.layout_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_splash)

        val topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_down)
        val downAnimation = AnimationUtils.loadAnimation(this, R.anim.down_top)

        imgSplash.animation = topAnimation
        textView2.animation = downAnimation

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, 500)
    }
}