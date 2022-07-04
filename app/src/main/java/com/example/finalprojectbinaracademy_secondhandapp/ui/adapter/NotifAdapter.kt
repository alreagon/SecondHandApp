package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.NotificationResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.NotificationResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.utils.ConvertNumberTo
import kotlinx.android.synthetic.main.notifikasi_item_buyer.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*
import kotlin.collections.ArrayList

class NotifAdapter() : RecyclerView.Adapter<NotifAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<NotificationResponseItem>() {
        override fun areItemsTheSame(oldItem: NotificationResponseItem, newItem: NotificationResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NotificationResponseItem, newItem: NotificationResponseItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(data: ArrayList<NotificationResponseItem>) {
        differ.submitList(data)
    }

    fun clearData() {
        val data = arrayListOf<NotificationResponseItem>()
        differ.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notifikasi_item_buyer, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentList = differ.currentList[position]

        holder.itemView.tvBidPrice.text = ConvertNumberTo().rupiah(currentList.bidPrice.toDouble())

        if (currentList.imageUrl == null) {
            Glide.with(holder.itemView.context)
                .load(R.drawable.default_photo)
                .centerCrop()
                .into(holder.itemView.ivProductImg)
            holder.itemView.ivProductImg.setPadding(16,16,16,16)
        } else {
            Glide.with(holder.itemView.context)
                .load(currentList.imageUrl)
                .centerCrop()
                .into(holder.itemView.ivProductImg)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}