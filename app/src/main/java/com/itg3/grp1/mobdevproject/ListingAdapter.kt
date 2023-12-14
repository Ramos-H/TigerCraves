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

class ListingAdapter(val dataset: List<Listing>, val userId: Int) : RecyclerView.Adapter<ListingAdapter.ViewHolder>()
{
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        var itemImage: ImageView
        var title: TextView
        var price: TextView
        var description: TextView
        var ratingBar: RatingBar

        init
        {
            itemImage = itemView.findViewById(R.id.imageview)
            title = itemView.findViewById(R.id.title)
            price = itemView.findViewById(R.id.price)
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

        if(currentListing.PriceMin == null && currentListing.PriceMax == null)
        {
            holder.price.visibility = View.GONE
        }
        else if(currentListing.PriceMin != null && currentListing.PriceMax == null)
        {
            holder.price.text = "${String.format("%.2f", currentListing.PriceMin)}PHP (Min. Price)"
        }
        else if(currentListing.PriceMin == null && currentListing.PriceMax != null)
        {
            holder.price.text = "${String.format("%.2f", currentListing.PriceMax)}PHP (Max Price)"
        }
        else
        {
            holder.price.text = "${String.format("%.2f", currentListing.PriceMin)}PHP - ${String.format("%.2f", currentListing.PriceMax)}PHP"
        }

        val resourceId = currentListing.Images?.first()?.ResourceId
        if(resourceId != null)
        {
            holder.itemImage.setImageResource(resourceId)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailedListingActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("listingId", currentListing.Id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = dataset.size
}