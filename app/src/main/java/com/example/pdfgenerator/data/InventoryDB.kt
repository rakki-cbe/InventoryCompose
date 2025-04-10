package com.example.pdfgenerator.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.data.customer.CustomerDao

@Database(entities = [Customer::class], version = 2)
abstract class InventoryDB: RoomDatabase() {
    abstract fun customerDao():CustomerDao
}