package com.reworld.pablo384.reworld.UI.fragments


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


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_fragment_recycle, container, false)
        view.tag="recycle"
        return view
    }


}// Required empty public constructor
