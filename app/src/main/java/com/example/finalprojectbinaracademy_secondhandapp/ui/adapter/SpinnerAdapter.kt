package com.example.finalprojectbinaracademy_secondhandapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.finalprojectbinaracademy_secondhandapp.R
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.CategoryResponseItem
import kotlinx.android.synthetic.main.spinner_list_category_item.view.*

class SpinnerAdapter(private val mContext: Context, categoryList: List<CategoryResponseItem>) : ArrayAdapter<CategoryResponseItem>(mContext, 0,categoryList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return setView(position,convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return setView(position,convertView,parent)
    }

    private fun setView(position: Int, convertView: View?, parent: ViewGroup): View {
        val category = getItem(position)

        val view = convertView ?: LayoutInflater.from(mContext)
            .inflate(R.layout.spinner_list_category_item, parent, false)

        view.tvCategory.text = category?.name

        return view

    }

}