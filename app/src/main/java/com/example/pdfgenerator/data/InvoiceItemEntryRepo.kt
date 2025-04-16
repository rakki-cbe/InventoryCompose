package com.example.pdfgenerator.data

import com.example.pdfgenerator.data.customer.Inventory
import com.example.pdfgenerator.data.customer.ItemsInventory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvoiceItemEntryRepo @Inject constructor(private val invoiceItemEntryDao: InvoiceItemEntryDao) {
    fun getAllBranch(): List<ItemsInventory> {
        return invoiceItemEntryDao.getAll()
    }

    fun saveCustomerData(item: ItemsInventory) {
        invoiceItemEntryDao.insertAll(item)
    }
}