package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.NotificationItemResponse
import com.example.finalprojectbinaracademy_secondhandapp.utils.convertISOTimeToDate
import com.example.finalprojectbinaracademy_secondhandapp.utils.rupiah
import kotlinx.android.synthetic.main.notifikasi_item_buyer.view.*
import kotlin.collections.ArrayList

class NotifAdapter(private val onItemClick: OnClickListener) : RecyclerView.Adapter<NotifAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<NotificationItemResponse>() {
        override fun areItemsTheSame(oldItem: NotificationItemResponse, newItem: NotificationItemResponse): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NotificationItemResponse, newItem: NotificationItemResponse): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(data: ArrayList<NotificationItemResponse>) {
        differ.submitList(data)
    }

    fun clearData() {
        val data = arrayListOf<NotificationItemResponse>()
        differ.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notifikasi_item_buyer, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentList = differ.currentList[position]

        if (position == 0) {
            holder.itemView.divider4.visibility = View.GONE
        }

        when(currentList.status) {
            "create" -> {
                holder.itemView.tvNotifType.text = "Berhasil diterbitkan"
                holder.itemView.tvNotifBidPrice.visibility = View.GONE
            }
            "bid" -> {
                holder.itemView.tvNotifType.text = "Penawaran produk"
                holder.itemView.tvNotifBidPrice.text = "Ditawar ${rupiah(currentList.bidPrice.toDouble())}"
            }
        }

        holder.itemView.tvDateOrder.text = convertISOTimeToDate(currentList.createdAt)
        holder.itemView.tvPriceProduct.text = rupiah(currentList.basePrice.toDouble())
        holder.itemView.tvNameProduct.text = currentList.productName

        if (currentList.read) {
            holder.itemView.ivReadFalse.visibility = View.GONE
        }

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

        holder.itemView.notifItem.setOnClickListener {
            onItemClick.onClickItem(currentList)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnClickListener {
        fun onClickItem(data: NotificationItemResponse)
    }
}