package com.reworld.pablo384.reworld.UI.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.reworld.pablo384.reworld.R
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import android.support.annotation.NonNull
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null


    companion object {
        val TAG = "LoginActivity.kt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        login_email.setOnClickListener {

            sigIn(email = email.text.toString(),password = password.text.toString())
        }

        createHere.setOnClickListener { startActivity(Intent(baseContext,CreateAccountActivity::class.java)) }


    }

    fun sigIn(email:String,password:String){
        mAuth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(LoginActivity.TAG, "signInWithEmail:success")
                        val user = mAuth?.getCurrentUser()

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(LoginActivity.TAG, "signInWithEmail:failure", task.getException())
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                    }

                    // ...
                }
    }

    init {
        Log.d("ads","const")
    }
}
