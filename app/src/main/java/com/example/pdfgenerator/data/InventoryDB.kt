package com.example.pdfgenerator.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pdfgenerator.data.customer.BranchDao
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.data.customer.CustomerDao
import com.example.pdfgenerator.data.customer.Inventory
import com.example.pdfgenerator.data.customer.ItemMasterEntryDao
import com.example.pdfgenerator.data.customer.ItemsInventory
import com.example.pdfgenerator.data.customer.ItemsMasterEntry
import com.example.pdfgenerator.data.customer.Profile

@Database(
    entities = [Profile::class, Customer::class, ItemsMasterEntry::class, Inventory::class,
        ItemsInventory::class], version = 3
)
abstract class InventoryDB: RoomDatabase() {
    abstract fun customerDao():CustomerDao
    abstract fun profileDao(): BranchDao
    abstract fun itemMasterEntryDao(): ItemMasterEntryDao
    abstract fun invoiceDao(): InvoiceDao
    abstract fun invoiceItemDao(): InvoiceItemEntryDao

}