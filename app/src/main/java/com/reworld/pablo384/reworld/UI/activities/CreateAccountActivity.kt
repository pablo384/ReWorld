package com.reworld.pablo384.reworld.UI.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.reworld.pablo384.reworld.R
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult
import android.support.annotation.NonNull
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.activity_create_account.*
import org.jetbrains.anko.toast


class CreateAccountActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null


    companion object {
        val TAG = "CreateAccountActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        mAuth = FirebaseAuth.getInstance()
        joinUs.setOnClickListener {
            createAccount(email_createAccount.text.toString(), password = password_createAccount.text.toString())
        }

    }
    fun createAccount(email:String, password:String){
        mAuth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(CreateAccountActivity.TAG, "createUserWithEmail:success")
                        toast("createUserWithEmail:success")
                        val user = mAuth?.currentUser

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(CreateAccountActivity.TAG, "createUserWithEmail:failure", task.getException())
                        Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()

                    }

                }
    }
}
