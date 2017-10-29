package com.reworld.pablo384.reworld.util

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity

/**
 * Created by pablo384 on 11/10/17.
 */
val REQUEST_IMAGE_CAPTURE = 1
val REQUEST_TAKE_PHOTO = 1


fun procedApplicationWithoutStory(thisActivity:Activity, nextActivity: Class<*>) {
    val intent = Intent(thisActivity.baseContext, nextActivity)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
    thisActivity.startActivity(intent)
    thisActivity.finish()
}
fun procedApplicationWithoutStory(thisActivity:AppCompatActivity, nextActivity: Class<*>) {
    val intent = Intent(thisActivity.baseContext, nextActivity)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP and Intent.FLAG_ACTIVITY_CLEAR_TASK and Intent.FLAG_ACTIVITY_NEW_TASK)
    thisActivity.startActivity(intent)
    thisActivity.finish()
}