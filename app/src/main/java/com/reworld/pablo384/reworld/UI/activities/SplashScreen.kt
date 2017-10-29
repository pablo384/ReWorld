package com.reworld.pablo384.reworld.UI.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.reworld.pablo384.reworld.util.procedApplicationWithoutStory


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser != null){
            procedApplicationWithoutStory(this,MainActivity::class.java)
        }else{
            procedApplicationWithoutStory(this,LoginFBActivity::class.java)
        }
    }
}
