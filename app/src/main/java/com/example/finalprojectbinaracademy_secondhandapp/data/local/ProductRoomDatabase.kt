package com.example.finalprojectbinaracademy_secondhandapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.CategoryTypeConverter
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.model.GetProductResponseItem

//@Database(entities = [GetProductResponseItem::class], version = 1)
@TypeConverters(CategoryTypeConverter::class)
abstract class ProductRoomDatabase : RoomDatabase() {
    abstract fun ProductDao() :ProductDao
}