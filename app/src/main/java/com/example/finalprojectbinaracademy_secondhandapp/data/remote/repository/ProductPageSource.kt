package com.example.finalprojectbinaracademy_secondhandapp.data.remote.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiHelperImpl

class ProductPageSource(private val apiHelperImpl: ApiHelperImpl): PagingSource<Int,GetProductResponseItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetProductResponseItem> {
        return try {
            val position = params.key ?: 1
            val params = HashMap<String,String>()
            params["page"] = position.toString()
            params["per_page"] = "20"
            val response = apiHelperImpl.getBuyerProduct(params)

            return LoadResult.Page(
                data = response.body()!!,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == 25) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GetProductResponseItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}