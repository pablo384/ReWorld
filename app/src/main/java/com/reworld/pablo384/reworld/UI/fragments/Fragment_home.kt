package com.reworld.pablo384.reworld.UI.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.reworld.pablo384.reworld.R
import kotlinx.android.synthetic.main.fragment_fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class Fragment_home : Fragment() {

    var mlisten:ListenerHome?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_fragment_home, container, false)

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
