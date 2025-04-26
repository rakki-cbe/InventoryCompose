package com.example.pdfgenerator.data.repository

import com.example.pdfgenerator.data.dao.InvoiceItemEntryDao
import com.example.pdfgenerator.data.model.ItemsInventory
import javax.inject.Inject

class InvoiceItemEntryRepo @Inject constructor(private val invoiceItemEntryDao: InvoiceItemEntryDao) {
    fun getAllBranch(): List<ItemsInventory> {
        return invoiceItemEntryDao.getAll()
    }

    fun saveInvoiceLineItem(item: ItemsInventory) {
        invoiceItemEntryDao.insertAll(item)
    }
}