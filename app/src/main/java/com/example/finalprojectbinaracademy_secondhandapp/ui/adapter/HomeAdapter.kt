package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.ItemProductHomeBinding
import kotlinx.android.synthetic.main.item_product_home.view.*

class HomeAdapter(private val productBuyerHome: List<GetProductResponseItem>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

//    companion object {
//        const val posterBaseUrl = "https://image.tmdb.org/t/p/w500"
//    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GetProductResponseItem)
    }

//    class ViewHolder(var binding: ItemProductHomeBinding) : RecyclerView.ViewHolder(binding.root)


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_product_home, parent, false))


        //        val binding =
//            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productResponse = productBuyerHome[position]
        val name = productResponse.name
        val description = productResponse.description
        val base_price = productResponse.basePrice.toString()
        val image_name_url = productResponse.imageUrl

//        Log.d("adapter", "onBindViewHolder: $poster")
//        holder.binding.apply {
//            Glide.with(holder.itemView.context).load(image_name_url).into(ivGambar)

        holder.itemView.iv_judul.text = name
        holder.itemView.iv_kategori.text = description
        holder.itemView.iv_basePrice.text = base_price
        holder.itemView.iv_cardView.setOnClickListener {
            onItemClickCallback.onItemClicked(productBuyerHome[position])
        }
//        ivJudul.text = name
//            ivKategori.text = description
//            ivBasePrice.text = base_price
//            ivCardView.setOnClickListener {
//                onItemClickCallback.onItemClicked(productBuyerHome[position])
//            }
//        }
    }


    override fun getItemCount() = productBuyerHome.size









    }
