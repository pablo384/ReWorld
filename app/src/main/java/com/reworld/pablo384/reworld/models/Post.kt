package com.reworld.pablo384.reworld.models

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * Created by pablo384 on 11/10/17.
 */
data class Post (
//        val authorKey: String,
                 val authorName: String? = FirebaseAuth.getInstance().currentUser?.displayName,
                 val description:String,
                 val date:Long,
                 val latitude:Double,
                 val longitude:Double,
                 val image:String,
                 var followers:ArrayList<String>?=ArrayList(),
                 var picker:String?=null,
                 var pickup:Boolean?=false,
                 var pickupDate:Long?=null
                 )
//var followers:List<User>?,
//var pickupDate:Int?