package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.databinding.ItemProductHomeBinding
import com.example.finalprojectbinaracademy_secondhandapp.utils.rupiah
import kotlinx.android.synthetic.main.item_product_home.view.*


class HomeAdapter: RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private var list = ArrayList<GetProductResponseItem>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GetProductResponseItem)
    }
    class ViewHolder(var binding: ItemProductHomeBinding) : RecyclerView.ViewHolder(binding.root)

    fun submitData(data: List<GetProductResponseItem>) {
        list.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemProductHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val productResponse = list[position]
        val name = productResponse.name
        val kategori = productResponse.categories
        val base_price = productResponse.basePrice.toDouble()
        val image_name_url = productResponse.imageUrl


        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(image_name_url)
                .listener(object : RequestListener<Drawable>{
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
            ivJudul.text = name
            if (!kategori.isEmpty()){
                ivKategori.text = kategori[0].name
            }else{
                ivKategori.text = "Kategori kosong"
            }
            ivBasePrice.text = rupiah(base_price)
            ivCardView.setOnClickListener {
                onItemClickCallback.onItemClicked(list[holder.adapterPosition])
            }

        }
    }


    override fun getItemCount() = list.size


}
