package com.example.finalprojectbinaracademy_secondhandapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.*

@Database(entities = [Product::class,Banner::class,Notification::class,SellerProduct::class,SellerOrder::class], version = 1)
@TypeConverters(CategoryTypeConverter::class,NotificationTypeConverter::class)
abstract class MyDatabase: RoomDatabase() {
    abstract fun localDao(): LocalDao
}