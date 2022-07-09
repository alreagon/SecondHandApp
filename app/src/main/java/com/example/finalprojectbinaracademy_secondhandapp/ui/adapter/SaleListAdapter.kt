package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.utils.rupiah
import kotlinx.android.synthetic.main.sale_list_product_item.view.*

class SaleListAdapter(private val onCLickItem: HomeAdapter.OnItemClickCallback): RecyclerView.Adapter<SaleListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<GetProductResponseItem>() {
        override fun areItemsTheSame(oldItem: GetProductResponseItem, newItem: GetProductResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GetProductResponseItem, newItem: GetProductResponseItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    private val differ = AsyncListDiffer(this,diffCallback)

    fun submitData(data: ArrayList<GetProductResponseItem>) {
        differ.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.sale_list_product_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentList = differ.currentList[position]

        holder.itemView.tvNameProductList.text = currentList.name
        holder.itemView.tvCategorySaleList.text = currentList.categories[0].name
        holder.itemView.tvPriceSaleList.text = rupiah(currentList.basePrice.toDouble())
        Glide.with(holder.itemView.context)
            .load(currentList.imageUrl)
            .centerCrop()
            .into(holder.itemView.ivProductSaleList)

        holder.itemView.saleListDimitaniItem.setOnClickListener {
            onCLickItem.onItemClicked(currentList)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}