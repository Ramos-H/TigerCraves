package com.itg3.grp1.mobdevproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){

        private var title = arrayOf(
            "Restaurant1", "Restaurant2", "Restaurant3", "Restaurant4", "Restaurant5",
            "Restaurant6", "Restaurant7", "Restaurant8", "Restaurant9", "Restaurant10",
            "Restaurant11", "Restaurant12", "Restaurant13", "Restaurant14", "Restaurant15",
            "Restaurant16", "Restaurant17", "Restaurant18", "Restaurant19", "Restaurant20")
        private var description = arrayOf(
            "Restaurant1", "Restaurant2", "Restaurant3", "Restaurant4", "Restaurant5",
            "Restaurant6", "Restaurant7", "Restaurant8", "Restaurant9", "Restaurant10",
            "Restaurant11", "Restaurant12", "Restaurant13", "Restaurant14", "Restaurant15",
            "Restaurant16", "Restaurant17", "Restaurant18", "Restaurant19", "Restaurant20")
        private var images = intArrayOf(
            R.drawable.imgtst, R.drawable.imgtst, R.drawable.imgtst, R.drawable.imgtst, R.drawable.imgtst,
            R.drawable.imgtst, R.drawable.imgtst, R.drawable.imgtst, R.drawable.imgtst, R.drawable.imgtst,
            R.drawable.imgtst, R.drawable.imgtst, R.drawable.imgtst, R.drawable.imgtst, R.drawable.imgtst,
            R.drawable.imgtst, R.drawable.imgtst, R.drawable.imgtst, R.drawable.imgtst, R.drawable.imgtst)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.title.text = title[position]
        holder.description.text = description [position]
        holder.itemImage.setImageResource(images[position])
        holder.ratingBar.rating

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            Toast.makeText(context, "Restaurant ${position + 1} pressed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return title.size
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemImage: ImageView
        var title: TextView
        var description: TextView
        var ratingBar: RatingBar

        init{
            itemImage = itemView.findViewById(R.id.imageview)
            title = itemView.findViewById(R.id.title)
            description = itemView.findViewById(R.id.description)
            ratingBar = itemView.findViewById(R.id.ratingBar)
        }
    }
}