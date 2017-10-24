package com.reworld.pablo384.reworld.models

import java.io.Serializable

/**
 * Created by pablo384 on 11/10/17.
 */
data class User(var name:String,
                val email:String,
                var picture:String,
                var task:ArrayList<Post>?=null) : Serializable