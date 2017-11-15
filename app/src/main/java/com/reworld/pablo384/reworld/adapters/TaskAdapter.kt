package com.reworld.pablo384.reworld.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.reworld.pablo384.reworld.R
import com.reworld.pablo384.reworld.models.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_post_items.view.*
import kotlinx.android.synthetic.main.layout_task_items.view.*

/**
 * Created by pablo384 on 15/11/17.
 */
class TaskAdapter(var postList:ArrayList<Post>, val itemClickListener: OnItemClickListener, val buttonClickListener: OnButtonClickListener, val buttonCancelLIstener:OnButtonClickListener) : RecyclerView.Adapter<TaskAdapter.HourViewHolder>() {

    override fun onBindViewHolder(holder: TaskAdapter.HourViewHolder, position: Int) {
        return holder.bind(postList[position], itemClickListener, buttonClickListener )
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder
            =HourViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_task_items,parent,false))


    override fun getItemCount(): Int= postList.size

    fun setItems(post:ArrayList<Post>){

        postList.clear()
        postList.addAll(post)
        notifyDataSetChanged()

    }


    class HourViewHolder(postItemView: View): RecyclerView.ViewHolder(postItemView){
        fun bind(post: Post, onItemClickListener: OnItemClickListener, onButtonClickListener: OnButtonClickListener)= with(itemView){
            textViewTaskName.text=post.authorName
            textViewTaskDescription.text=post.description
            buttonPickupTask.setOnClickListener { onButtonClickListener.onButtonClick(post,position) }
            buttonCancel.setOnClickListener { onButtonClickListener.onButtonClick(post,position = position, id = "cancel") }
            setOnClickListener { onItemClickListener.onItemClick(post, position) }
            Picasso.with(context).load(post.image)
                    .into(imageViewTasktimage)
            if (post.picker !=null) {
                textViewTaskCatchTask.text = "picked by: ${post.picker}"
            }
            if (post.pickup==false) buttonCancel.visibility=View.VISIBLE
            else buttonCancel.visibility=View.INVISIBLE
        }
    }

    interface OnItemClickListener{
        fun onItemClick(post: Post, position:Int)
    }
    interface OnButtonClickListener{
        fun onButtonClick(post: Post, position:Int, id:String?="pickup")
    }
}