package com.example.finalprojectbinaracademy_secondhandapp.data.local.db

import com.example.finalprojectbinaracademy_secondhandapp.data.local.model.*

class LocalDaoHelperImpl(private val localDao: LocalDao) {

    // product
    fun getAllProduct() = localDao.getAllProducts()
    suspend fun deleteAndInsertData(products: List<Product>) = localDao.deleteAndInsertData(products)

    //banner
    fun getALlBanner() = localDao.getAllBanners()
    suspend fun deleteInsertBanner(banner: List<Banner>) = localDao.deleteAndInsertBanner(banner)

    //notif
    fun getAllNotification() = localDao.getAllNotification()
    suspend fun deleteInsertNotif(notif: List<Notification>) = localDao.deleteAndInsertNotif(notif)

    //seller product
    fun getAllProductSeller() = localDao.getAllSellerProduct()
    suspend fun deleteAndInsertDataSeller(products: List<SellerProduct>) = localDao.deleteAndInsertDataSeller(products)

    //seller order
    fun getAllOrderSeller() = localDao.getAllSellerOrder()
    suspend fun deleteAndInsertOrderSeller(orders: List<SellerOrder>) = localDao.deleteAndInsertOrderSeller(orders)
}