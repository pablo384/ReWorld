package com.reworld.pablo384.reworld.UI.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.reworld.pablo384.reworld.R
import com.reworld.pablo384.reworld.models.User

class TaskToRecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_to_recycle)
        val user = intent.getSerializableExtra("us") as User

        Log.d("TAG", user.toString())
    }
}
