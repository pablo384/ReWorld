package com.reworld.pablo384.reworld.UI.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.reworld.pablo384.reworld.R
import kotlinx.android.synthetic.main.activity_login_fb.*
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult


class LoginFBActivity : AppCompatActivity() {
    var callbackManager:CallbackManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(baseContext)
        AppEventsLogger.activateApp(baseContext)
        setContentView(R.layout.activity_login_fb)



        callbackManager = CallbackManager.Factory.create()

        login_with_email.setOnClickListener { startActivity(Intent(baseContext, LoginActivity::class.java)) }

        login_button.setReadPermissions("email")
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {

            }

            override fun onSuccess(result: LoginResult?) {

            }

            override fun onError(error: FacebookException?) {

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode,resultCode,data)

    }
}
