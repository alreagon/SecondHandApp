package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.BannerResponse
import kotlinx.android.synthetic.main.notifikasi_item_buyer.view.*
import kotlinx.android.synthetic.main.slider_item.view.*


class ImageSliderAdapter(
private val data: List<BannerResponse>
) : RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.slider_item, parent, false)
        )
    }


override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val currentList = data[position]

    if (currentList.image_url == null) {
        Glide.with(holder.itemView.context)
            .load(R.drawable.shopeebanner1)
            .centerCrop()
            .into(holder.itemView.iv_slider)
    } else {
        Glide.with(holder.itemView.context)
            .load(currentList.image_url)
            .centerCrop()
            .into(holder.itemView.iv_slider)
    }

}

override fun getItemCount(): Int {
    return data.size
}

}