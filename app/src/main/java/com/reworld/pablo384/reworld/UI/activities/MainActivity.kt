package com.reworld.pablo384.reworld.UI.activities

import android.support.v4.app.Fragment
import android.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.reworld.pablo384.reworld.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import android.support.annotation.NonNull
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import com.reworld.pablo384.reworld.UI.fragments.Fragment_account
import com.reworld.pablo384.reworld.UI.fragments.Fragment_home
import com.reworld.pablo384.reworld.UI.fragments.Fragment_recycle
import com.reworld.pablo384.reworld.UI.fragments.Fragment_setting


class MainActivity : AppCompatActivity(), Fragment_home.ListenerHome,
        Fragment_account.ListenerAccount {

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
                            toast("presionaste home")
                            setFragment(supportFragmentManager.findFragmentByTag("home"),"home")

                            return@OnNavigationItemSelectedListener true
                        }
                        R.id.action_camera -> {
                            toast("presionaste recycle")
                            setFragment(Fragment_recycle(),"recycle")

                            return@OnNavigationItemSelectedListener true
                        }
                        R.id.action_account -> {
                            toast("presionaste account")
                            setFragment(Fragment_account(),"account")
                            return@OnNavigationItemSelectedListener true
                        }
                        R.id.action_setting -> {
                            toast("presionaste account")
                            setFragment(Fragment_setting(),"setting")
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

}
