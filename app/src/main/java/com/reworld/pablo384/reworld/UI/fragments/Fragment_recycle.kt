package com.reworld.pablo384.reworld.UI.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.reworld.pablo384.reworld.R


/**
 * A simple [Fragment] subclass.
 */
class Fragment_recycle : Fragment() {


    var mlisten:ListenerRecycle?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_fragment_recycle, container, false)
        view.tag="recycle"
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            mlisten = context as ListenerRecycle
        }catch (e:Exception){
            e.printStackTrace()
        }
        if (context is ListenerRecycle){
            mlisten = context
        }else{
            throw RuntimeException(context!!.toString() + " must implement ListenerRecycler")
        }

    }

    override fun onResume() {
        super.onResume()
        mlisten?.selectedBottomR(R.id.action_camera)
    }


    interface ListenerRecycle{
        fun selectedBottomR(name:Int)
    }



}// Required empty public constructor
