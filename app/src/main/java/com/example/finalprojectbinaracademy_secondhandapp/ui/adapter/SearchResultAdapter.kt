package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter
//
//import android.annotation.SuppressLint
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.finalprojectbinaracademy_secondhandapp.R
//import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
//import kotlinx.android.synthetic.main.item_product_home.view.*
//
//
//class SearchResultAdapter(private val onClick: (GetProductResponseItem) -> Unit) :
//    RecyclerView.Adapter<SearchResultAdapter.ViewHolder>() {
//
//    private var listBuyerProductSearch: List<GetProductResponseItem>? = null
//    fun setDataBuyerProductSearch(list: List<GetProductResponseItem>) {
//        this.listBuyerProductSearch = list
//    }
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): ViewHolder{
//        return ViewHolder(LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_product_home, parent, false))
//    }
//
//    @SuppressLint("SetTextI18n")
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        with(holder.itemView){
//            with(listBuyerProductSearch!![position]){
//                Glide.with(iv_gambar.context)
//                    .load(imageUrl)
//                    .error(R.drawable.default_photo)
//                    .into(iv_gambar)
//
//                iv_judul.text = name
//                iv_basePrice.text = basePrice.toString()
//
//                if(categories!!.isNotEmpty()){
//                    for(i in categories.indices){
//                        if (categories.lastIndex == 0) {
//                            iv_kategori.text = "Kategori: " + categories[i].name
//                            break
//                        }
//                        if (i == 0) {
//                            iv_kategori.text = "Kategori: " + categories[i].name + ", "
//                        } else if (i != categories.lastIndex && i > 0) {
//                            iv_kategori.text =
//                                iv_kategori.text.toString() + categories[i].name + ", "
//                        } else {
//                            iv_kategori.text =
//                                iv_kategori.text.toString() + categories[i].name
//                        }
//                    }
//                }else{
//                    iv_kategori.text = "Kategori: lainnya"
//                }
//
//                iv_cardView.setOnClickListener {
//                    onClick(listBuyerProductSearch!![position])
//                }
//
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return if(listBuyerProductSearch.isNullOrEmpty()){
//            0
//        }else{
//            listBuyerProductSearch!!.size
//        }
//    }
//
//}
