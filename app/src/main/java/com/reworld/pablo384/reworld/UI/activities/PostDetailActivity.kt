package com.reworld.pablo384.reworld.UI.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.reworld.pablo384.reworld.R
import com.reworld.pablo384.reworld.util.POSTS_LIST
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_post_detail.*

class PostDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        intent.let {
            val mypost = POSTS_LIST[it.getIntExtra("position",0)]
            Picasso.with(baseContext).load(mypost.image).into(postDetailImage)
            postDetailDescription.text=mypost.description
        }
    }
}
