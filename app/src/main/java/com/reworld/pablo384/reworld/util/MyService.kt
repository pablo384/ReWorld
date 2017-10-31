package com.reworld.pablo384.reworld.util

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Intent

/**
 * Created by pablo384 on 31/10/17.
 */

@SuppressLint("Registered")

class MyService : IntentService("RewordService") {
    companion object {
        val ACTION_PROGRESO = "com.reworld.pablo384.reworld.PROGRESO"
        val ACTION_FIN = "com.reworld.pablo384.reworld.FIN"
    }
    override fun onHandleIntent(workIntent: Intent) {
        val iter:Int  = workIntent.getIntExtra("interaciones",0)
        var i=1
        while (i<iter){
            i++
            tareaLarga()
            val int = Intent()
            int.setAction(ACTION_PROGRESO)
            int.putExtra("progreso",i*10)
            sendBroadcast(int)
        }
        val fin = Intent()
        fin.setAction(ACTION_FIN)
        sendBroadcast(fin)


    }
    private fun tareaLarga(){
        try {
            Thread.sleep(1000)
        }catch (e:InterruptedException){}
    }

}
