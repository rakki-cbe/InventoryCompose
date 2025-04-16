package com.example.pdfgenerator.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pdfgenerator.data.customer.Inventory
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {
    @Query("SELECT * FROM inventory")
    fun getAll(): List<Inventory>

    @Query("SELECT * FROM inventory WHERE billId IN (:billerId)")
    fun loadAllByIds(billerId: IntArray): List<Inventory>

    @Query("SELECT * FROM inventory WHERE date LIKE :date")
    fun findByName(date: String): Inventory

    @Insert
    fun insert(inventory: Inventory): Long

    @Query("SELECT billId FROM inventory WHERE rowid = :rowId")
    fun getBillerId(rowId: Long): Long

    @Delete
    fun delete(inventory: Inventory)
}