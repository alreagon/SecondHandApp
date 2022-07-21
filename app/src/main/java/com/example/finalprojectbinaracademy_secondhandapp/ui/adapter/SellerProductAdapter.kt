package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.SellerProduct
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.utils.rupiah
import kotlinx.android.synthetic.main.head_rv_seller_product.view.*
import kotlinx.android.synthetic.main.sale_list_product_item.view.*

private val ITEM_VIEW_TYPE_HEADER = 0
private val ITEM_VIEW_TYPE_ITEM = 1

class SellerProductAdapter(private val handleClick: SellerProductOnClick): ListAdapter<DataItem,RecyclerView.ViewHolder>(SellerProductDiffUtil()) {
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    class HeaderViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.ProductItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    fun SubmitListWithHeader(list: List<SellerProduct>?) {
        val items = when (list) {
            null -> listOf(DataItem.Header)
            else -> listOf(DataItem.Header) + list.map { DataItem.ProductItem(it) }
        }

        submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_VIEW_TYPE_HEADER ->
                HeaderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.head_rv_seller_product,parent,false))
            ITEM_VIEW_TYPE_ITEM ->
                ItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sale_list_product_item,parent,false))
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ItemViewHolder -> {
                val currentList = getItem(position) as DataItem.ProductItem
                val item = currentList.getProductResponseItem

                holder.itemView.tvNameProductList.text = item.name
                holder.itemView.tvCategorySaleList.text = item.categories[0].name
                holder.itemView.tvPriceSaleList.text = rupiah(item.basePrice.toDouble())
                Glide.with(holder.itemView.context)
                    .load(item.imageUrl)
                    .centerCrop()
                    .into(holder.itemView.ivProductSaleList)

                holder.itemView.saleListDimitaniItem.setOnClickListener {
                    handleClick.onItemClick(item)
                }
            }
            is HeaderViewHolder -> {
                holder.itemView.ivGoToPostProduct.setOnClickListener {
                    handleClick.onHeaderClick()
                }
            }
        }
    }

}

interface SellerProductOnClick {
    fun onItemClick(data: SellerProduct)
    fun onHeaderClick()
}

class SellerProductDiffUtil : DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    abstract val id: Long
    data class ProductItem(val getProductResponseItem: SellerProduct): DataItem() {
        override val id: Long = getProductResponseItem.id.toLong()
    }

    object Header : DataItem() {
        override val id: Long
            get() = Long.MIN_VALUE
    }
}