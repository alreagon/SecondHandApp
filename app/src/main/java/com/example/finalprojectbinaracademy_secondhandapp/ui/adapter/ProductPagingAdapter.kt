package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.utils.rupiah
import kotlinx.android.synthetic.main.item_product_home.view.*

class ProductPagingAdapter(): PagingDataAdapter<GetProductResponseItem,ProductPagingAdapter.ViewHolder>(COMPARATOR) {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    private lateinit var onItemClick: OnItemClickCallback

    fun onItemClick(onItemClick: OnItemClickCallback) {
        this.onItemClick = onItemClick
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productResponse = getItem(position)
        productResponse?.let {
            Glide.with(holder.itemView.context)
                .load(it.imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.itemView.iv_gambar.setImageResource(R.drawable.default_photo)
                        return true
                    }
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                })
                .into(holder.itemView.iv_gambar)

            holder.itemView.iv_judul.text = it.name
            if (!it.categories.isEmpty()) {
                holder.itemView.iv_kategori.text = it.categories[0].name
            } else {
                holder.itemView.iv_kategori.text = "Kategori kosong"
            }
            holder.itemView.iv_basePrice.text = rupiah(it.basePrice.toDouble())
            holder.itemView.iv_cardView.setOnClickListener {
                onItemClick.onItemClicked(productResponse)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.item_product_home,parent,false)
        return ViewHolder(inflater)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<GetProductResponseItem>() {
            override fun areItemsTheSame(oldItem: GetProductResponseItem, newItem: GetProductResponseItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GetProductResponseItem, newItem: GetProductResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GetProductResponseItem)
    }
}