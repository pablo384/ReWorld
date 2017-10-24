package com.reworld.pablo384.reworld.models

/**
 * Created by pablo384 on 11/10/17.
 */
data class Post (val author:User,
                 val description:String,
                 val date:Int,
                 val location:String,
                 val image:String,
                 var followers:ArrayList<User>?=ArrayList(),
                 var picker:User?=null,
                 var pickup:Boolean?=false,
                 var pickupDate:Int?=null
                 )
//var followers:List<User>?,
//var pickupDate:Int?