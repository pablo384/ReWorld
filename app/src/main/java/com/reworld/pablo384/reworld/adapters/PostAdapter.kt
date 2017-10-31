package com.reworld.pablo384.reworld.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.reworld.pablo384.reworld.R
import com.reworld.pablo384.reworld.models.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_post_items.view.*

/**
 * Created by pablo384 on 11/10/17.
 */
class PostAdapter(var postList:ArrayList<Post>, val itemClickListener: OnItemClickListener,val buttonClickListener: OnButtonClickListener) : RecyclerView.Adapter<PostAdapter.HourViewHolder>() {
    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        return holder.bind(postList[position], itemClickListener, buttonClickListener )

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder
            =HourViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_post_items,parent,false))


    override fun getItemCount(): Int= postList.size

    fun setItems(post:ArrayList<Post>){

        postList.clear()
        postList.addAll(post)
        notifyDataSetChanged()

    }


    class HourViewHolder(postItemView: View):RecyclerView.ViewHolder(postItemView){
        fun bind(post:Post, onItemClickListener: OnItemClickListener, onButtonClickListener: OnButtonClickListener)= with(itemView){
            textViewPostName.text=post.authorName
            textViewPostDescription.text=post.description
            buttonPickup.setOnClickListener { onButtonClickListener.onButtonClick(post,position) }
            setOnClickListener { onItemClickListener.onItemClick(post, position) }
            Picasso.with(context).load(post.image)
                    .into(imageViewPostimage)
            if (post.picker !=null) {
                textViewCatch.text = "picked by: ${post.picker}"
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(post:Post, position:Int)
    }
    interface OnButtonClickListener{
        fun onButtonClick(post:Post, position:Int)
    }
}