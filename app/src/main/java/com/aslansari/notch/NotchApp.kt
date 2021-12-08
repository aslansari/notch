package com.aslansari.notch

import android.app.Application
import com.aslansari.notch.bucket.CartRepository
import com.aslansari.notch.bucket.persistence.NotchDatabase

class NotchApp: Application() {


    val database by lazy { NotchDatabase.getDatabase(this) }
    val repository by lazy { CartRepository(database.getItemDAO()) }
}