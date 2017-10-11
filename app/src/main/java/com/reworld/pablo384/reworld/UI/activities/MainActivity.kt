package com.reworld.pablo384.reworld.UI.activities

import android.support.v4.app.Fragment
import android.app.FragmentManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.reworld.pablo384.reworld.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentActivity
import android.view.MenuItem
import com.reworld.pablo384.reworld.UI.fragments.Fragment_account
import com.reworld.pablo384.reworld.UI.fragments.Fragment_home
import com.reworld.pablo384.reworld.UI.fragments.Fragment_recycle
import com.reworld.pablo384.reworld.UI.fragments.Fragment_setting


class MainActivity : FragmentActivity(), Fragment_home.ListenerHome,
        Fragment_account.ListenerAccount, Fragment_recycle.ListenerRecycle {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val frtr = supportFragmentManager.beginTransaction()
        frtr.add(R.id.content_fragment,Fragment_home(),"home")
        frtr.commit()

        navigation.selectedItemId = R.id.action_home

        navigation.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.getItemId()) {
                        R.id.action_home -> {
                            val currentF:Fragment? = supportFragmentManager.findFragmentById(R.id.content_fragment)
                            val home:Fragment? = supportFragmentManager.findFragmentByTag("home")
                            if( currentF == home) {

                            }else{
                                setFragment(Fragment_home(),"home")
                            }


                            return@OnNavigationItemSelectedListener true
                        }
                        R.id.action_camera -> {
                            val currentF:Fragment? = supportFragmentManager.findFragmentById(R.id.content_fragment)
                            val recycle:Fragment? = supportFragmentManager.findFragmentByTag("recycle")
                            if( currentF == recycle ) {

                            }else{
                                setFragment(Fragment_recycle(),"recycle")
                            }


                            return@OnNavigationItemSelectedListener true
                        }
                        R.id.action_account -> {
                            val currentF:Fragment? = supportFragmentManager.findFragmentById(R.id.content_fragment)
                            val account:Fragment? = supportFragmentManager.findFragmentByTag("account")
                            if( currentF == account) {

                            }else{
                                setFragment(Fragment_account(),"account")
                            }

                            return@OnNavigationItemSelectedListener true
                        }
                    }
                    false
                }
        )

    }

    override fun selectedBottomH(name: Int) {
        navigation.selectedItemId = name
    }

    override fun selectedBottomA(name: Int) {
        navigation.selectedItemId = name
    }

    override fun selectedBottomR(name: Int) {
        navigation.selectedItemId = name
    }

    override fun onBackPressed() {
        super.onBackPressed()
//        if (supportFragmentManager.findFragmentByTag("home").isVisible){
//
//        }

    }

    private fun setFragment(fragment:Fragment, tag:String) {
        val frtr = supportFragmentManager.beginTransaction()
        frtr.replace(R.id.content_fragment, fragment, tag)
        frtr.addToBackStack(tag)
        frtr.commit()
    }
    private fun setFragmenthome(fragment:Fragment, tag:String) {
        val frtr = supportFragmentManager.beginTransaction()
        frtr.add(R.id.content_fragment, fragment, tag)
//        frtr.addToBackStack(tag)
        frtr.commit()
    }

}
