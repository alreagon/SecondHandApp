package com.example.finalprojectbinaracademy_secondhandapp.data.local

import androidx.room.*
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponse
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM getProductHome")
    fun getAllProductHome() : Flow <List<GetProductResponse>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(getProductResponse: List<GetProductResponse>)

    @Query("DELETE FROM getProductHome")
    suspend fun deleteAllProductResponse()
}