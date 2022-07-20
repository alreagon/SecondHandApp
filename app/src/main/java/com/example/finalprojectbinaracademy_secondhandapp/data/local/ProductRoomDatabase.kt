package com.example.finalprojectbinaracademy_secondhandapp.data.local

import androidx.room.RoomDatabase

//@Database(entities = [GetProductResponseItem::class], version = 1)
//@TypeConverters(CategoryTypeConverter::class)
abstract class ProductRoomDatabase : RoomDatabase() {
    abstract fun ProductDao() :ProductDao
}