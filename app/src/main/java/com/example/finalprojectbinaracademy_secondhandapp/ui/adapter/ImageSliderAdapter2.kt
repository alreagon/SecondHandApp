package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.databinding.SliderItemBinding
import com.example.finalprojectbinaracademy_secondhandapp.utils.imageData

class ImageSliderAdapter2(private val items: List<imageData>) : RecyclerView.Adapter<ImageSliderAdapter2.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: SliderItemBinding) : RecyclerView.ViewHolder(itemView.root){
        private val binding = itemView
        fun bind(data:imageData){
            with(binding){
                Glide.with(itemView)
                    .load((data.imageDrawable))
//                    .into(ivSlider)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return  ImageViewHolder(SliderItemBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])

    }

    override fun getItemCount(): Int = items.size

}