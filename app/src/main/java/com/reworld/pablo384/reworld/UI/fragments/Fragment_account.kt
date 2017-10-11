package com.reworld.pablo384.reworld.UI.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.reworld.pablo384.reworld.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_fragment_account.view.*


/**
 * A simple [Fragment] subclass.
 */
class Fragment_account : Fragment() {

    var mlisten:ListenerAccount?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_fragment_account, container, false)
        var url = "https://lh3.googleusercontent.com/-COuBXkid6RA/AAAAAAAAAAI/AAAAAAAAAAA/ACnBePZK49qlMSdfStfOkCuLMk4Yy-Uaaw/s96-c-mo/photo.jpg"
        with(view){
            Picasso.with(context).load(url).into(profile_image)
        }
        // Inflate the layout for this fragment
        return view
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
