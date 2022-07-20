package com.example.finalprojectbinaracademy_secondhandapp.data.local

import androidx.room.withTransaction
import com.example.finalprojectbinaracademy_secondhandapp.data.remote.service.ApiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

//class RemotRepoTest (
//    private val api: ApiService,
//    private val db: ProductRoomDatabase
//) {
//    private val restaurantDao = db.ProductDao()
//
//    fun getRestaurants() : Flow<Resource<Unit>> = networkBoundResource(
//        query = {
//            ProductDao.getAllProductHome()
//        },
//        fetch = {
//            delay(2000)
//            api.getBuyerProduct()
//        },
//        saveFetchResult = {
//            db.withTransaction {
//                restaurantDao.deleteAllProductResponse()
//                restaurantDao.insert(it)
//            }
//        }
//    )
//}