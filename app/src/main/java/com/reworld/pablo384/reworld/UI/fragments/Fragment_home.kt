package com.reworld.pablo384.reworld.UI.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.reworld.pablo384.reworld.R
import com.reworld.pablo384.reworld.UI.activities.MainActivity
import com.reworld.pablo384.reworld.adapters.PostAdapter
import com.reworld.pablo384.reworld.models.Post
import com.reworld.pablo384.reworld.models.User
import kotlinx.android.synthetic.main.fragment_fragment_home.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Fragment_home : Fragment() {

    var mlisten:ListenerHome?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_fragment_home, container, false)
        var url = "http://i.imgur.com/DvpvklR.png"
        var post:ArrayList<Post> = ArrayList()
        post.add(Post(User("Pablo Reinoso","pablo@gmail.com",url),"Esto es una simple prueba",
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),"-50",url))
        post.add(Post(User("Pablo Reinoso","pablo@gmail.com",url),"Esto es una simple prueba",
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),"-50",url))

        with(view){

            findViewById<RecyclerView>(R.id.my_recycler_view_post).setHasFixedSize(true)
            findViewById<RecyclerView>(R.id.my_recycler_view_post).layoutManager = LinearLayoutManager(context)
            findViewById<RecyclerView>(R.id.my_recycler_view_post).adapter = PostAdapter(post)
        }


        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            mlisten = context as ListenerHome
        }catch (e:Exception){
            e.printStackTrace()
        }
        if (context is ListenerHome){
            mlisten = context
        }else{
            throw RuntimeException(context!!.toString() + " must implement ListenerHome")
        }

    }


    override fun onResume() {
        super.onResume()
        mlisten?.selectedBottomH(R.id.action_home)

    }

    interface ListenerHome{
        fun selectedBottomH(name:Int)
    }

}// Required empty public constructor
