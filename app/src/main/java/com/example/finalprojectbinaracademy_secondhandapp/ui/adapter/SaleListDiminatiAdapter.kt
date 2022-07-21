package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.SellerOrder
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetSellerOrderResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.utils.convertISOTimeToDate
import com.example.finalprojectbinaracademy_secondhandapp.utils.rupiah
import kotlinx.android.synthetic.main.notifikasi_item_buyer.view.*

class SaleListDiminatiAdapter(private val onClick: OnCLickItem): RecyclerView.Adapter<SaleListDiminatiAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<SellerOrder>() {
        override fun areItemsTheSame(oldItem: SellerOrder, newItem: SellerOrder): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SellerOrder, newItem: SellerOrder): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(data: List<SellerOrder>) {
        differ.submitList(data)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.notifikasi_item_buyer, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentList = differ.currentList[position]

        if (position == 0) {
            holder.itemView.divider4.visibility = View.GONE
        }

        holder.itemView.ivReadFalse.visibility = View.GONE
        holder.itemView.tvDateOrder.text = convertISOTimeToDate(currentList.createdAt)
        holder.itemView.tvNameProduct.text = currentList.productName
        holder.itemView.tvPriceProduct.text = rupiah(currentList.basePrice.toDouble())
        holder.itemView.tvNotifBidPrice.text = "Ditawar ${rupiah(currentList.price.toDouble())}"
        if (currentList.status == "accepted") {
            holder.itemView.tvPriceProduct.paintFlags = holder.itemView.tvPriceProduct.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.itemView.tvNotifBidPrice.text = "Berhasil ditawar ${rupiah(currentList.price.toDouble())}"
        } else if (currentList.status == "declined") {
            holder.itemView.tvNotifBidPrice.paintFlags = holder.itemView.tvNotifBidPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {

        }

        currentList.product?.let {
            Glide.with(holder.itemView.context)
                .load(it.imageUrl)
                .centerCrop()
                .into(holder.itemView.ivProductImg)
        }

        holder.itemView.notifItem.setOnClickListener {
            onClick.onClickItemListener(currentList)
        }

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnCLickItem {
        fun onClickItemListener(data: SellerOrder)
    }
}