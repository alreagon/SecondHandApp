package com.example.finalprojectbinaracademy_secondhandapp.data.local.db

import androidx.room.*
import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.*

@Dao
interface LocalDao {

    //product
    @Query("SELECT * FROM products")
    fun getAllProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(products: List<Product>)

    @Query("DELETE FROM products")
    suspend fun deleteAllProduct()

    @Transaction
    suspend fun deleteAndInsertData(products: List<Product>) {
        deleteAllProduct()
        insertProduct(products)
    }

    //banner
    @Query("SELECT * FROM banners")
    fun getAllBanners(): List<Banner>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBanner(banner: List<Banner>)

    @Query("DELETE FROM banners")
    suspend fun deleteAllBanner()

    @Transaction
    suspend fun deleteAndInsertBanner(banner: List<Banner>) {
        deleteAllBanner()
        insertBanner(banner)
    }

    //notif
    @Query("SELECT * FROM notifications")
    fun getAllNotification(): List<Notification>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotif(notif: List<Notification>)

    @Query("DELETE FROM notifications")
    suspend fun deleteAllNotif()

    @Transaction
    suspend fun deleteAndInsertNotif(notif: List<Notification>) {
        deleteAllNotif()
        insertNotif(notif)
    }

    //product seller
    @Query("SELECT * FROM seller_products")
    fun getAllSellerProduct(): List<SellerProduct>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductSeller(products: List<SellerProduct>)

    @Query("DELETE FROM seller_products")
    suspend fun deleteAllProductSeller()

    @Transaction
    suspend fun deleteAndInsertDataSeller(products: List<SellerProduct>) {
        deleteAllProductSeller()
        insertProductSeller(products)
    }

    //order seller
    @Query("SELECT * FROM seller_orders")
    fun getAllSellerOrder(): List<SellerOrder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderSeller(products: List<SellerOrder>)

    @Query("DELETE FROM seller_orders")
    suspend fun deleteAllOrderSeller()

    @Transaction
    suspend fun deleteAndInsertOrderSeller(orders: List<SellerOrder>) {
        deleteAllOrderSeller()
        insertOrderSeller(orders)
    }
}