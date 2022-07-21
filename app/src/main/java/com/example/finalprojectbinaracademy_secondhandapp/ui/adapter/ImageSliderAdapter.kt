package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.Banner
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.BannerResponse
import com.example.finalprojectbinaracademy_secondhandapp.databinding.SliderItemBinding
import kotlinx.android.synthetic.main.slider_item.view.*


class ImageSliderAdapter(
    private val data: List<Banner>
) : RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {


    class ViewHolder(var binding: SliderItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            SliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentList = data[position]

        if (currentList.image_url == null) {
            Glide.with(holder.itemView.context)
                .load(R.drawable.default_photo)
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