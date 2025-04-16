package com.example.pdfgenerator.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pdfgenerator.data.customer.Inventory
import com.example.pdfgenerator.data.customer.ItemsInventory
import kotlinx.coroutines.flow.Flow

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