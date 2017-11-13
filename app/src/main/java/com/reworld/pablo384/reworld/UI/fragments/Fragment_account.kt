package com.reworld.pablo384.reworld.UI.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

import com.reworld.pablo384.reworld.R
import com.reworld.pablo384.reworld.UI.activities.*
import com.reworld.pablo384.reworld.models.User
import com.reworld.pablo384.reworld.util.USER_LOG
import com.reworld.pablo384.reworld.util.procedApplicationWithoutStory
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
        mAuth = FirebaseAuth.getInstance()
        with(view){

            Picasso.with(context).load(USER_LOG?.photoUrl).into(profile_image)

            btLogOut.setOnClickListener { logOut() }
            btCalculator.setOnClickListener({
                startActivity(Intent(context,CalculatorActivity::class.java))
            })
            btTaskRecycle.setOnClickListener {
                startActivity(Intent(context,TaskToRecycleActivity::class.java)) }

            btreadQR.setOnClickListener {
                startActivity(Intent(context,SimpleScannerActivity::class.java))

            }
        }
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
        if (LoginManager.getInstance() != null){
            LoginManager.getInstance().logOut()
        }
        mAuth?.signOut()
        procedApplicationWithoutStory(activity,LoginFBActivity::class.java)



    }



    interface ListenerAccount{
        fun selectedBottomA(name:Int)
    }

}// Required empty public constructor
