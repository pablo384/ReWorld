package com.reworld.pablo384.reworld.models

import java.io.Serializable

/**
 * Created by pablo384 on 11/10/17.
 */
data class User(val key:String,
                val id:String,
                var name:String,
                val email:String,
                var photoUrl:String="null",
                var tasks:ArrayList<Post>?=null) : Serializable