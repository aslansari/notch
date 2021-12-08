package com.aslansari.notch.bucket.persistence

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: Item)

    @Update
    suspend fun updateItem(vararg item: Item)

    @Query("DELETE FROM items WHERE id=:id")
    suspend fun deleteItem(id: Int)

    @Query("SELECT * FROM items")
    fun getItems(): Flow<List<Item>>

}