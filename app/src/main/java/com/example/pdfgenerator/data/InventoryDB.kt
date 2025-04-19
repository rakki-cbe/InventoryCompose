package com.example.pdfgenerator.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pdfgenerator.data.dao.BranchDao
import com.example.pdfgenerator.data.dao.CustomerDao
import com.example.pdfgenerator.data.dao.InvoiceDao
import com.example.pdfgenerator.data.dao.InvoiceItemEntryDao
import com.example.pdfgenerator.data.dao.ItemMasterEntryDao
import com.example.pdfgenerator.data.model.Customer
import com.example.pdfgenerator.data.model.Inventory
import com.example.pdfgenerator.data.model.ItemsInventory
import com.example.pdfgenerator.data.model.ItemsMasterEntry
import com.example.pdfgenerator.data.model.Profile

@Database(
    entities = [Profile::class, Customer::class, ItemsMasterEntry::class, Inventory::class,
        ItemsInventory::class], version = 3
)
abstract class InventoryDB: RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun profileDao(): BranchDao
    abstract fun itemMasterEntryDao(): ItemMasterEntryDao
    abstract fun invoiceDao(): InvoiceDao
    abstract fun invoiceItemDao(): InvoiceItemEntryDao

}