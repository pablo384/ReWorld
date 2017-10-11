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
class Fragment_account : Fragment() {

    var mlisten:ListenerAccount?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_fragment_account, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            mlisten = context as ListenerAccount
        }catch (e:Exception){
            e.printStackTrace()
        }
        if (context is ListenerAccount){
            mlisten = context
        }else{
            throw RuntimeException(context!!.toString() + " must implement ListenerAccount")
        }

    }

    override fun onResume() {
        super.onResume()
        mlisten?.selectedBottomA(R.id.action_account)
    }



    interface ListenerAccount{
        fun selectedBottomA(name:Int)
    }

}// Required empty public constructor
