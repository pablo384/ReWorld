package com.reworld.pablo384.reworld.UI.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (FirebaseAuth.getInstance().currentUser != null){
            procedApplication(MainActivity::class.java)
        }else{
            procedApplication(LoginFBActivity::class.java)
        }


    }

    private fun procedApplication(clase: Class<*>) {
        val intent = Intent(this@SplashScreen, clase)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent)
        finish()
    }

}
