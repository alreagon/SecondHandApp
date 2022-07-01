package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.BannerResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.NotificationResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.ItemProductHomeBinding
import com.example.finalprojectbinaracademy_secondhandapp.databinding.SliderItemBinding
import com.example.finalprojectbinaracademy_secondhandapp.utils.ConvertNumberTo
import com.example.finalprojectbinaracademy_secondhandapp.utils.imageData
import kotlinx.android.synthetic.main.notifikasi_item_buyer.view.*
import kotlinx.android.synthetic.main.slider_item.view.*

class ImageSliderAdapter() : RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<BannerResponse>() {
        override fun areItemsTheSame(oldItem: BannerResponse, newItem: BannerResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BannerResponse, newItem: BannerResponse): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(data: ArrayList<BannerResponse>) {
        differ.submitList(data)
    }

    fun clearData() {
        val data = arrayListOf<BannerResponse>()
        differ.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.slider_item, parent, false)
        )
    }


override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val currentList = differ.currentList[position]

    if (currentList.image == null) {
        Glide.with(holder.itemView.context)
            .load(R.drawable.aamiin)
            .centerCrop()
            .into(holder.itemView.iv_slider)
    } else {
        Glide.with(holder.itemView.context)
            .load(currentList.image)
            .centerCrop()
            .into(holder.itemView.iv_slider)
    }

}

override fun getItemCount(): Int {
    return differ.currentList.size
}

}