package com.itg3.grp1.mobdevproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itg3.grp1.mobdevproject.data.models.Review

class ReviewAdapter(val dataset: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>()
{
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var title: TextView
        var content: TextView
        var datePosted: TextView
        var rating: TextView

        init
        {
            title = itemView.findViewById(R.id.title)
            content = itemView.findViewById(R.id.content)
            datePosted = itemView.findViewById(R.id.datePosted)
            rating = itemView.findViewById(R.id.rating)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewAdapter.ViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_review, parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int)
    {
        val currentReview = dataset[position]

        holder.title.text = currentReview.Title
        holder.content.text = currentReview.Content
        holder.datePosted.text = currentReview.DatePosted?.date.toString()
        holder.rating.text = currentReview.Rating!!.toString()
    }

    override fun getItemCount() = dataset.size
}