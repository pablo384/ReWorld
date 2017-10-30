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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.reworld.pablo384.reworld.R
import com.reworld.pablo384.reworld.adapters.PostAdapter
import com.reworld.pablo384.reworld.models.Post
import com.reworld.pablo384.reworld.models.User
import org.jetbrains.anko.support.v4.toast
import java.util.*
import kotlin.collections.ArrayList
import android.content.Intent
import android.net.Uri


/**
 * A simple [Fragment] subclass.
 */
class Fragment_home : Fragment(), PostAdapter.OnItemClickListener, PostAdapter.OnButtonClickListener {

    var mlisten:ListenerHome?=null
    var post:ArrayList<Post> = ArrayList()
    var task:ArrayList<Post> = ArrayList()
    var url = "http://4.bp.blogspot.com/-bnM7ZcjKlKo/TiOiBRsNzSI/AAAAAAAAADM/0nRbxeuJPSQ/s1600/Imagenes+2011+050.jpg"
    val usuarioPablo = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_fragment_home, container, false)
        val postAdapter = PostAdapter(post,
        this@Fragment_home,this@Fragment_home)
        val fireBD = FirebaseDatabase.getInstance().getReference("Post")
        fireBD.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                post.removeAll(post)
//                p0.children.mapNotNullTo(post) { it.getValue<Post>(Post::class.java) }
                for (ds:DataSnapshot in p0.children){
                    val auName = ds.child("authorName").getValue(String::class.java) as String
                    val image = ds.child("image").getValue(String::class.java) as String
                    val date = ds.child("date").getValue(Long::class.java) as Long
                    val description = ds.child("description").getValue(String::class.java) as String
                    val latitude = ds.child("latitude").getValue(Double::class.java) as Double
                    val longitude = ds.child("longitude").getValue(Double::class.java) as Double
                    post.add(Post(auName,description,date,latitude,longitude,image))
                }
                postAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })


        with(view){

            findViewById<RecyclerView>(R.id.my_recycler_view_post).setHasFixedSize(true)
            findViewById<RecyclerView>(R.id.my_recycler_view_post).layoutManager = LinearLayoutManager(context)
            findViewById<RecyclerView>(R.id.my_recycler_view_post).adapter = postAdapter
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

        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Pickup", { dialog, whichButton ->
//                    deleteCity(position)
                    val item:Post = post[position]
                    val uri:Uri = Uri.parse("http://maps.google.com/maps?daddr=${item.latitude},${item.longitude}")
                    showMap(uri)
                })
                .setNegativeButton("Cancel", null).show()
    }

    fun showMap(geoLocation: Uri) {
        val intent = Intent(Intent.ACTION_VIEW,geoLocation)
//        intent.data = geoLocation
        if (intent.resolveActivity(activity.packageManager) != null) {
            startActivity(intent)
        }
    }

    interface ListenerHome{
        fun selectedBottomH(name:Int)
        fun sendTask(user:User)
    }

}// Required empty public constructor
