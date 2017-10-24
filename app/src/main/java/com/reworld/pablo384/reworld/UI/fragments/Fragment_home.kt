package com.reworld.pablo384.reworld.UI.fragments


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.reworld.pablo384.reworld.R
import com.reworld.pablo384.reworld.adapters.PostAdapter
import com.reworld.pablo384.reworld.models.Post
import com.reworld.pablo384.reworld.models.User
import org.jetbrains.anko.support.v4.toast
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class Fragment_home : Fragment(), PostAdapter.OnItemClickListener, PostAdapter.OnButtonClickListener {

    var mlisten:ListenerHome?=null
    var post:ArrayList<Post> = ArrayList()
    var task:ArrayList<Post> = ArrayList()
    var url = "http://4.bp.blogspot.com/-bnM7ZcjKlKo/TiOiBRsNzSI/AAAAAAAAADM/0nRbxeuJPSQ/s1600/Imagenes+2011+050.jpg"
    val usuarioPablo = User("Pablo Reinoso","usuarioPablo@gmail.com",url)

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_fragment_home, container, false)

        post.add(Post(usuarioPablo,"Esto es una simple prueba",
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),"-50",url, picker = usuarioPablo))
        post.add(Post(usuarioPablo,"Esto es una simple prueba",
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),"-50",url, picker = usuarioPablo))

        with(view){

            findViewById<RecyclerView>(R.id.my_recycler_view_post).setHasFixedSize(true)
            findViewById<RecyclerView>(R.id.my_recycler_view_post).layoutManager = LinearLayoutManager(context)
            findViewById<RecyclerView>(R.id.my_recycler_view_post).adapter = PostAdapter(post,
                    this@Fragment_home,this@Fragment_home)
        }


        return view
    }

    override fun onItemClick(post: Post, position: Int) {
        toast("CLICK EN VISTA")
    }

    override fun onButtonClick(post: Post, position: Int) {
        toast("CLICK EN BOTON")
        showAlertForPickupPost("Recojer","Esta seguro que quiere recoger estos reciduos?",position)

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

    private fun showAlertForPickupPost(title: String, message: String, position: Int) {

        val dialog = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Remove", { dialog, whichButton ->
//                    deleteCity(position)
                    val lista:Post = post[position]
                    lista.followers?.add(usuarioPablo)
                    task.add(lista)
//                    usuarioPablo.task= task
                    mlisten?.sendTask(usuarioPablo)
                    toast("Will be pick up ${lista.followers?.last()}")
                })
                .setNegativeButton("Cancel", null).show()
    }

    interface ListenerHome{
        fun selectedBottomH(name:Int)
        fun sendTask(user:User)
    }

}// Required empty public constructor
