package com.reworld.pablo384.reworld.UI.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth

import com.reworld.pablo384.reworld.R
import com.reworld.pablo384.reworld.UI.activities.LoginFBActivity
import com.reworld.pablo384.reworld.UI.activities.MainActivity
import com.reworld.pablo384.reworld.UI.activities.TaskToRecycleActivity
import com.reworld.pablo384.reworld.models.User
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_fragment_account.view.*
import org.jetbrains.anko.support.v4.toast


/**
 * A simple [Fragment] subclass.
 */
class Fragment_account : Fragment() {

    var mlisten:ListenerAccount?=null
    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_fragment_account, container, false)
        var url = "https://lh3.googleusercontent.com/-COuBXkid6RA/AAAAAAAAAAI/AAAAAAAAAAA/ACnBePZK49qlMSdfStfOkCuLMk4Yy-Uaaw/s96-c-mo/photo.jpg"
        with(view){
            Picasso.with(context).load(url).into(profile_image)
        }
        mAuth = FirebaseAuth.getInstance()
        with(view){
            btLogOut.setOnClickListener { logOut() }
            btCalculator.setOnClickListener({
                val i = Intent(context,LoginFBActivity::class.java)
                startActivity(i)



            })
            btTaskRecycle.setOnClickListener {
                val act = activity as MainActivity
                toast(act.getData().toString())
                startActivity(Intent(context,TaskToRecycleActivity::class.java).putExtra("us",act.getData())) }
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
    fun logOut(){
        mAuth?.signOut()

    }



    interface ListenerAccount{
        fun selectedBottomA(name:Int)
    }

}// Required empty public constructor
