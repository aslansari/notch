package com.aslansari.notch.bucket

import com.aslansari.notch.bucket.persistence.Item
import com.aslansari.notch.bucket.persistence.ItemDAO

class CartRepository(private val itemDAO: ItemDAO) {

    val itemListFlow = itemDAO.getItems()

    suspend fun addItem(item: Item) {
        itemDAO.insertItem(item)
    }

    suspend fun updateItem(item: Item) {
        itemDAO.updateItem(item)
    }

    suspend fun deleteItem(id: Int) {
        itemDAO.deleteItem(id)
    }

}