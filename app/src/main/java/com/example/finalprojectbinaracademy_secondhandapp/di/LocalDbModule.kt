package com.example.finalprojectbinaracademy_secondhandapp.di

import android.content.Context
import androidx.room.Room
import com.example.finalprojectbinaracademy_secondhandapp.data.local.datastore.DataStoreManager
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.MyDatabase
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDao
import com.example.finalprojectbinaracademy_secondhandapp.data.local.db.LocalDaoHelperImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDbModule = module {
    single { DataStoreManager(androidContext()) }
    single { provideDb(androidContext()) }
    single { provideProductDao(get()) }
    single { provideProductDaoImpl(get()) }
}

private fun provideDb(context: Context) : MyDatabase {
    return Room.databaseBuilder(context,MyDatabase::class.java,"my_db")
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()
}

private fun provideProductDao(myDatabase: MyDatabase): LocalDao {
    return myDatabase.localDao()
}

private fun provideProductDaoImpl(localDao: LocalDao): LocalDaoHelperImpl {
    return LocalDaoHelperImpl(localDao)
}
