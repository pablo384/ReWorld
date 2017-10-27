package com.reworld.pablo384.reworld.UI.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.reworld.pablo384.reworld.R
import kotlinx.android.synthetic.main.activity_login_fb.*
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginResult
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import android.util.Log
import com.google.firebase.auth.FacebookAuthProvider
import com.facebook.AccessToken
import org.jetbrains.anko.toast


class LoginFBActivity : AppCompatActivity() {
    var callbackManager:CallbackManager?=null
    private var mAuth: FirebaseAuth? = null

    companion object {
        val TAG = "LoginFBActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(baseContext)
        AppEventsLogger.activateApp(baseContext)
        setContentView(R.layout.activity_login_fb)

        mAuth = FirebaseAuth.getInstance()


        callbackManager = CallbackManager.Factory.create()

        login_with_email.setOnClickListener { startActivity(Intent(baseContext, LoginActivity::class.java)) }

        login_button.setReadPermissions("email")
        login_button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onCancel() {

            }

            override fun onSuccess(result: LoginResult) {
                handleFacebookAccessToken(result.accessToken)

            }

            override fun onError(error: FacebookException?) {

            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager?.onActivityResult(requestCode,resultCode,data)

    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth?.currentUser
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d(LoginFBActivity.TAG, "handleFacebookAccessToken:" + token)

        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth?.signInWithCredential(credential)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(LoginFBActivity.TAG, "signInWithCredential:success")
                        val user = mAuth?.currentUser
                        toast("welcome ${user?.displayName}")
                        procedApplication(MainActivity::class.java)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(LoginFBActivity.TAG, "signInWithCredential:failure", task.getException())
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                    }

                    // ...
                }
    }
    private fun procedApplication(clase: Class<*>) {
        val intent = Intent(this@LoginFBActivity, clase)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}
