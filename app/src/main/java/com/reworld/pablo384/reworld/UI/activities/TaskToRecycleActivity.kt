package com.reworld.pablo384.reworld.UI.activities


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.reworld.pablo384.reworld.R
import com.reworld.pablo384.reworld.adapters.TaskAdapter
import com.reworld.pablo384.reworld.models.Post
import com.reworld.pablo384.reworld.util.POST_LIST_TASK
import kotlinx.android.synthetic.main.layout_task_items.*

class TaskToRecycleActivity : AppCompatActivity(), TaskAdapter.OnItemClickListener, TaskAdapter.OnButtonClickListener {

    val postAdapter = TaskAdapter(POST_LIST_TASK,
            this, this, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_to_recycle)
        updateTask()

//        val user = intent.getSerializableExtra("us") as User

//        Log.d("TAG", user.toString())

//        buttonStart.setOnClickListener {
//            val tarea = Intent(this,MyService::class.java)
//            tarea.putExtra("interaciones",10)
//            startService(tarea)
//        }

//        val filter = IntentFilter()
//        filter.addAction(MyService.ACTION_PROGRESO)
//        filter.addAction(MyService.ACTION_FIN)
//        val reci = ProgressReciver()
//        registerReceiver(reci,filter)
    }

    //    private inner class ProgressReciver:BroadcastReceiver(){
//        override fun onReceive(context: Context, intent: Intent) {
//            if (intent.action.equals(MyService.ACTION_PROGRESO)){
//                var prog = intent.getIntExtra("progreso",0)
//                progressBar2.progress=prog
//            }else if (intent.action.equals(MyService.ACTION_FIN)){
//                toast("Tarea Finalizada")
//            }
//        }
//
//    }
    private fun updateTask() {
//        val postAdapter = TaskAdapter(POST_LIST_TASK,
//                this, this, this)
        findViewById<RecyclerView>(R.id.my_recycler_view_post_task).setHasFixedSize(true)
        findViewById<RecyclerView>(R.id.my_recycler_view_post_task).layoutManager = LinearLayoutManager(baseContext)
        findViewById<RecyclerView>(R.id.my_recycler_view_post_task).adapter = postAdapter

        findViewById<RecyclerView>(R.id.my_recycler_view_post_task).visibility = View.VISIBLE

    }

    override fun onItemClick(post: Post, position: Int) {

    }

    override fun onButtonClick(post: Post, position: Int, id: String?) {
        if (id!="pickup"){
            POST_LIST_TASK.removeAt(position)
            postAdapter.notifyDataSetChanged()
        }else{
            buttonPickupTask.isClickable=false
            buttonPickupTask.setBackgroundResource(R.color.colorPrimaryDark)
            buttonCancel.visibility=View.INVISIBLE
        }
    }
}
