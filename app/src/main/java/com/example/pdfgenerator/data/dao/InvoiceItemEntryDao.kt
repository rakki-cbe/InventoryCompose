package com.example.pdfgenerator.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pdfgenerator.data.model.ItemsInventory

@Dao
interface InvoiceItemEntryDao {
    @Query("SELECT * FROM itemsinventory")
    fun getAll(): List<ItemsInventory>

    @Query("SELECT * FROM itemsinventory WHERE itemInventoryId IN (:id)")
    fun loadAllByIds(id: IntArray): List<ItemsInventory>

    @Query("SELECT * FROM itemsinventory WHERE billId LIKE :billerID")
    fun findByName(billerID: String): ItemsInventory

    @Insert
    fun insertAll(vararg itemInventory: ItemsInventory)

    @Delete
    fun delete(itemInventory: ItemsInventory)
}