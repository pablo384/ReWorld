package com.reworld.pablo384.reworld.UI.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import com.reworld.pablo384.reworld.R
import com.reworld.pablo384.reworld.models.User
import com.reworld.pablo384.reworld.util.MyService
import kotlinx.android.synthetic.main.activity_task_to_recycle.*
import org.jetbrains.anko.toast

class TaskToRecycleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_to_recycle)
//        val user = intent.getSerializableExtra("us") as User

//        Log.d("TAG", user.toString())

        buttonStart.setOnClickListener {
            val tarea = Intent(this,MyService::class.java)
            tarea.putExtra("interaciones",10)
            startService(tarea)
        }

        val filter = IntentFilter()
        filter.addAction(MyService.ACTION_PROGRESO)
        filter.addAction(MyService.ACTION_FIN)
        val reci = ProgressReciver()
        registerReceiver(reci,filter)
    }
    inner class ProgressReciver:BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action.equals(MyService.ACTION_PROGRESO)){
                var prog = intent.getIntExtra("progreso",0)
                progressBar2.progress=prog
            }else if (intent.action.equals(MyService.ACTION_FIN)){
                toast("Tarea Finalizada")
            }
        }

    }
}
