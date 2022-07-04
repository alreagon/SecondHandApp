package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.ItemProductHomeBinding
import kotlinx.android.synthetic.main.item_product_home.view.*


class HomeAdapter(private val productBuyerHome: List<GetProductResponseItem>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GetProductResponseItem)
    }
    class ViewHolder(var binding: ItemProductHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productResponse = productBuyerHome[position]
        val name = productResponse.name
        val kategori = productResponse.categories
        val base_price = productResponse.basePrice.toString()
        val image_name_url = productResponse.imageUrl


        holder.binding.apply {
            Glide.with(holder.itemView.context).load(image_name_url).into(holder.itemView.iv_gambar)
            ivJudul.text = name
            if (!kategori.isEmpty()){
                ivKategori.text = kategori[0].name
            }else{
                ivKategori.text = "Kategori kosong"
            }
            ivBasePrice.text = base_price
            ivCardView.setOnClickListener {
                onItemClickCallback.onItemClicked(productBuyerHome[holder.adapterPosition])
            }

        }
    }


    override fun getItemCount() = productBuyerHome.size


}
