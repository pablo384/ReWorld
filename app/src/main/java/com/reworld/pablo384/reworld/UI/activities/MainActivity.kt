package com.reworld.pablo384.reworld.UI.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.reworld.pablo384.reworld.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_letsrecycle.setOnClickListener { toast("Vamos a Reciclar con ReWorld") }

//        navigation.setOnClickListener {
//            if (card_view.visibility == View.VISIBLE){
//                card_view.visibility= View.INVISIBLE
//                imageView.visibility=View.VISIBLE
//            }else{
//                card_view.visibility= View.VISIBLE
//                imageView.visibility=View.INVISIBLE
//            }
//        }
    }
}
