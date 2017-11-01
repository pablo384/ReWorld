package com.reworld.pablo384.reworld.util

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
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
val USERS_LIST:ArrayList<User> = ArrayList()
val POSTS_LIST:ArrayList<Post> = ArrayList()


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
//fun updatePost(){
//    val fireBD = FirebaseDatabase.getInstance().getReference("Post")
//    fireBD.addValueEventListener(object :ValueEventListener{
//        override fun onDataChange(p0: DataSnapshot) {
//            val post:ArrayList<Post> = ArrayList()
//            for (ds:DataSnapshot in p0.children){
//                val auName = ds.child("authorName").getValue(String::class.java) as String
//                val image = ds.child("image").getValue(String::class.java) as String
//                val date = ds.child("date").getValue(Long::class.java) as Long
//                val description = ds.child("description").getValue(String::class.java) as String
//                val latitude = ds.child("latitude").getValue(Double::class.java) as Double
//                val longitude = ds.child("longitude").getValue(Double::class.java) as Double
//                post.add(Post(auName,description,date,latitude,longitude,image))
//            }
//            POSTS_LIST.clear()
//            POSTS_LIST.addAll(post)
//
//        }
//
//        override fun onCancelled(p0: DatabaseError?) {
//        }
//    })
//}
//fun checkUser(){
//    val fireBD = FirebaseDatabase.getInstance().getReference("User")
//    fireBD.addValueEventListener(object : ValueEventListener {
//        override fun onDataChange(p0: DataSnapshot) {
//            USERS_LIST.removeAll(USERS_LIST)
//
//            for (ds: DataSnapshot in p0.children){
//                val email = ds.child("email").getValue(String::class.java) as String
//                val id = ds.child("id").getValue(String::class.java) as String
//                val key = ds.key as String
////                val date = ds.child("date").getValue(Long::class.java) as Long
//                val name = ds.child("name").getValue(String::class.java) as String
//                val photoUrl = ds.child("photoUrl").getValue(String::class.java) as String
////                val latitude = ds.child("latitude").getValue(Double::class.java) as Double
////                val longitude = ds.child("longitude").getValue(Double::class.java) as Double
//                if (FirebaseAuth.getInstance().currentUser?.uid == id){
//                    USER_LOG=User(key,id,name,email,photoUrl)
//                }
//                USERS_LIST.add(User(key,id,name,email,photoUrl))
//
//            }
//
//
//        }
//
//        override fun onCancelled(p0: DatabaseError?) {
//        }
//    })
//}
fun findUser(){

//    for (user in USERS_LIST){
//        Log.d("USER_AUTH",user.toString())
//        if (user.id==FirebaseAuth.getInstance().currentUser?.uid){
//            USER_LOG = user
//            Log.d("USER_AUTH","User found")
//
//        }
//    }
    val fbdb = FirebaseDatabase.getInstance().reference
    val query:Query = fbdb.child("User").orderByChild("id").equalTo(FirebaseAuth.getInstance().currentUser?.uid)
    query.addListenerForSingleValueEvent(object :ValueEventListener{
        override fun onCancelled(dataSnapshotError : DatabaseError?) {

        }

        override fun onDataChange(dataSnapshot : DataSnapshot) {
            if (dataSnapshot.exists()){
                for (adsa:DataSnapshot in dataSnapshot.children){
                    val email = adsa.child("email").getValue(String::class.java)
                    val id = adsa.child("id").getValue(String::class.java)
                    val key = adsa.key
                    val name = adsa.child("name").getValue(String::class.java)
                    val photoUrl = adsa.child("photoUrl").getValue(String::class.java)
                    USER_LOG = User(key,id.toString(),name.toString(),email.toString(),photoUrl.toString())

                    Log.d("USER_S", USER_LOG.toString())
                }
            }else{
                Log.d("USER_AUTH","User does not found")
                createUser()
                Log.d("USER_AUTH","User created")
            }
        }
    })


//    if (USER_LOG == null){
//        Log.d("USER_AUTH","User does not found")
//        createUser()
//        Log.d("USER_AUTH","User created")
//    }
}
fun createUser(){
    val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    val myRef = database.getReference("User").push()
    val fbUser = FirebaseAuth.getInstance().currentUser
    USER_LOG= User(myRef.key,fbUser!!.uid,fbUser.displayName.toString(),fbUser.email.toString(),fbUser.photoUrl.toString())
    myRef.setValue(USER_LOG)

}