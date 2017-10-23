package com.reworld.pablo384.reworld.UI.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.reworld.pablo384.reworld.R
import kotlinx.android.synthetic.main.activity_login_fb.*

class LoginFBActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_fb)

        login_with_email.setOnClickListener { startActivity(Intent(baseContext, LoginActivity::class.java)) }
    }
}
