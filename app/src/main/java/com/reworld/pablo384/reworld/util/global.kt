package com.reworld.pablo384.reworld.util

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.reworld.pablo384.reworld.models.Post
import com.reworld.pablo384.reworld.models.User

/**
 * Created by pablo384 on 11/10/17.
 */
val REQUEST_IMAGE_CAPTURE = 1
val REQUEST_TAKE_PHOTO = 1
var USER_KEY:String?=null
val FIREBASE_STORAGE_URL = "gs://reworld-3d5ed.appspot.com/images/"
val FIREBASE_STORAGE_IMAGES = "images"
var USER_LOG:User?=null


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
fun checkUser():ArrayList<User>{
    val userList = ArrayList<User>()
    val fireBD = FirebaseDatabase.getInstance().getReference("User")
    fireBD.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(p0: DataSnapshot) {
            userList.removeAll(userList)

            for (ds: DataSnapshot in p0.children){
                val email = ds.child("email").getValue(String::class.java) as String
                val id = ds.child("id").getValue(String::class.java) as String
                val key = ds.key as String
//                val date = ds.child("date").getValue(Long::class.java) as Long
                val name = ds.child("name").getValue(String::class.java) as String
                val photoUrl = ds.child("photoUrl").getValue(String::class.java) as String
//                val latitude = ds.child("latitude").getValue(Double::class.java) as Double
//                val longitude = ds.child("longitude").getValue(Double::class.java) as Double
                userList.add(User(key,id,name,email,photoUrl))

            }


        }

        override fun onCancelled(p0: DatabaseError?) {
        }
    })
    return userList
}
fun findUser(){
    val listUser = checkUser()
    for (user in listUser){
        if (user.id==FirebaseAuth.getInstance().currentUser?.uid){
            USER_LOG = user
        }
    }
}
fun createUser(){
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef = database.getReference("User").push()
    val fbUser = FirebaseAuth.getInstance().currentUser
    USER_LOG= User(myRef.key,fbUser!!.uid,fbUser.displayName.toString(),fbUser.photoUrl.toString())
    myRef.setValue(USER_LOG)


}