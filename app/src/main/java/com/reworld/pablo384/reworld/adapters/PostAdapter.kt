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
class PostAdapter(val postList:ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.HourViewHolder>() {
    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        return holder.bind(postList[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder
            =HourViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_post_items,parent,false))


    override fun getItemCount(): Int= postList.size


    class HourViewHolder(postItemView: View):RecyclerView.ViewHolder(postItemView){
        fun bind(post:Post)= with(itemView){
            textViewPostName.text=post.author.name
            textViewPostDescription.text=post.description
            Picasso.with(context).load(post.image).into(imageViewPostimage)
        }
    }
}