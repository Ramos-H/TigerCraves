package com.itg3.grp1.mobdevproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.itg3.grp1.mobdevproject.data.models.Listing

class ListingAdapter(val dataset: List<Listing>) : RecyclerView.Adapter<ListingAdapter.ViewHolder>()
{
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var itemImage: ImageView
        var title: TextView
        var description: TextView
        var ratingBar: RatingBar

        init
        {
            itemImage = itemView.findViewById(R.id.imageview)
            title = itemView.findViewById(R.id.title)
            description = itemView.findViewById(R.id.description)
            ratingBar = itemView.findViewById(R.id.ratingBar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingAdapter.ViewHolder
    {
       val v = LayoutInflater.from(parent.context).inflate(R.layout.cardview_listing,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ListingAdapter.ViewHolder, position: Int)
    {
        val currentListing = dataset[position]

        holder.title.text = currentListing.Name
        holder.description.text = currentListing.Address
        holder.itemImage.setImageResource(R.drawable.imgtst)
        holder.ratingBar.rating = currentListing.Rating!!.toFloat()

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            context.startActivity(Intent(context, DetailedListingActivity::class.java))
        }
    }

    override fun getItemCount() = dataset.size
}