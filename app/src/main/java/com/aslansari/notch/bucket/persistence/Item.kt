package com.aslansari.notch.bucket.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true) val id:Int,
    val name: String,
    var isAcquired: Boolean
) {
    constructor(name: String, isAcquired: Boolean) : this(0, name, isAcquired)
}
