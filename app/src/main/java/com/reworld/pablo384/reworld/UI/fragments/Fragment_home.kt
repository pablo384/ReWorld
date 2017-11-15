package com.reworld.pablo384.reworld.UI.fragments


import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.reworld.pablo384.reworld.R
import com.reworld.pablo384.reworld.adapters.PostAdapter
import com.reworld.pablo384.reworld.models.Post
import com.reworld.pablo384.reworld.models.User
import org.jetbrains.anko.support.v4.toast
import kotlin.collections.ArrayList
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.widget.ProgressBar
import com.reworld.pablo384.reworld.UI.activities.PostDetailActivity
import com.reworld.pablo384.reworld.util.MyService
import com.reworld.pablo384.reworld.util.POSTS_LIST
import com.reworld.pablo384.reworld.util.POST_LIST_TASK
import kotlinx.android.synthetic.main.activity_task_to_recycle.*
import kotlinx.android.synthetic.main.fragment_fragment_home.*
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Fragment_home : Fragment(), PostAdapter.OnItemClickListener, PostAdapter.OnButtonClickListener {

    var mlisten:ListenerHome?=null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater!!.inflate(R.layout.fragment_fragment_home, container, false)


        with(view){
            updatePost(view)

        }
//        val filter = IntentFilter()
//        filter.addAction(MyService.ACTION_PROGRESO)
//        filter.addAction(MyService.ACTION_FIN)
//        val reci = ProgressReciver()
//        activity.registerReceiver(reci,filter)
//
//        val tarea = Intent(context,MyService::class.java)
//        tarea.putExtra(MyService.FIREBASE_POST_HOME,1)
//        activity.startService(tarea)


        return view
    }

    private fun updatePost(view:View) {
        val postAdapter = PostAdapter(POSTS_LIST,
                this@Fragment_home,this@Fragment_home)
        //Pendiente de correr en segundo plano
        val fireBD = FirebaseDatabase.getInstance().getReference("Post")
        fireBD.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                POSTS_LIST.removeAll(POSTS_LIST)
                for (ds:DataSnapshot in p0.children){
                    val auName = ds.child("authorName").getValue(String::class.java) as String
                    val image = ds.child("image").getValue(String::class.java) as String
                    val date = ds.child("date").getValue(Long::class.java) as Long
                    val description = ds.child("description").getValue(String::class.java) as String
                    val latitude = ds.child("latitude").getValue(Double::class.java) as Double
                    val longitude = ds.child("longitude").getValue(Double::class.java) as Double
                    POSTS_LIST.add(Post(auName,description,date,latitude,longitude,image))
                }
                POSTS_LIST.reverse()
                postAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })





        with(view){
            findViewById<RecyclerView>(R.id.my_recycler_view_post).setHasFixedSize(true)
            findViewById<RecyclerView>(R.id.my_recycler_view_post).layoutManager = LinearLayoutManager(context)
            findViewById<RecyclerView>(R.id.my_recycler_view_post).adapter = postAdapter

            findViewById<ProgressBar>(R.id.progressBarHome).visibility=View.INVISIBLE
            findViewById<RecyclerView>(R.id.my_recycler_view_post).visibility=View.VISIBLE

        }

    }

    override fun onItemClick(post: Post, position: Int) {
        startActivity(Intent(context,PostDetailActivity::class.java).putExtra("position",position))
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
                    val item:Post = POSTS_LIST[position]
                    POST_LIST_TASK.add(item)
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

//    private inner class ProgressReciver: BroadcastReceiver(){
//        override fun onReceive(context: Context, intent: Intent) {
//            if (intent.action.equals(MyService.ACTION_PROGRESO)){
//                var prog = intent.getIntExtra("progreso",0)
//                progressBar2.progress=prog
//            }else if (intent.action.equals(MyService.ACTION_FIN)){
//                toast("Tarea Finalizada")
//
//
//
//            }
//        }
//
//    }

    interface ListenerHome{
        fun selectedBottomH(name:Int)
        fun sendTask(user:User)
    }

}// Required empty public constructor
