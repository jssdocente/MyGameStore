package com.pmdm.mygamestore

import android.app.Application
import androidx.room.Room
import com.pmdm.mygamestore.data.local.AppDatabase

class MyGameStoreApp : Application() {

    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "mygamestore_db"
        ).build()
    }
}
