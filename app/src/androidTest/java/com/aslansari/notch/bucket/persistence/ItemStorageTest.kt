package com.aslansari.notch.bucket.persistence

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ItemStorageTest {

    companion object {
        val item = Item(
            name = "Cheese",
            isAcquired = false
        )
    }

    private lateinit var itemDAO: ItemDAO
    private lateinit var db: NotchDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NotchDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        itemDAO = db.getItemDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeItemAndReadInList() = runBlocking {
        val itemToInsert = item.copy(name = "insertTest")
        itemDAO.insertItem(itemToInsert)
        val items = itemDAO.getItems().first()
        assert(items[0].name == itemToInsert.name)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndUpdateItemAndReadInList() = runBlocking {
        val itemToInsert = item.copy(name = "insertTest")
        itemDAO.insertItem(itemToInsert)
        val insertedItem = itemDAO.getItems().first()[0]
        assert(insertedItem.name == itemToInsert.name)

        val itemToUpdate = insertedItem.copy(name = "updatedItem")
        itemDAO.updateItem(itemToUpdate)
        val updatedItem = itemDAO.getItems().first()[0]
        assert(updatedItem.name == itemToUpdate.name)
    }

    @Test
    @Throws(Exception::class)
    fun writeAndDeleteItemAndReadInList() = runBlocking {
        val itemToInsert = item.copy(name = "insertTest")
        itemDAO.insertItem(itemToInsert)
        val items = itemDAO.getItems().first()
        assert(items[0].name == itemToInsert.name)

        itemDAO.deleteItem(items[0].id)
        val updatedItems = itemDAO.getItems().first()
        assert(updatedItems.isEmpty())
    }
}