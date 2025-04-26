package com.example.pdfgenerator.data.repository

import com.example.pdfgenerator.data.dao.ItemMasterEntryDao
import com.example.pdfgenerator.data.model.ItemsMasterEntry
import javax.inject.Inject

class ItemMasterEntryRepo @Inject constructor(private val itemMasterEntryDao: ItemMasterEntryDao) {
    fun getAllBranch(): List<ItemsMasterEntry> {
        return itemMasterEntryDao.getAll()
    }

    fun saveMasterItemEntry(item: ItemsMasterEntry) {
        var totalGst = 0.0
        val cgst = item.cgst.toLong()
        val sgst = item.sgst.toLong()
        val igst = item.igst.toLong()
        if (cgst > 0) totalGst += cgst
        if (sgst > 0) totalGst += sgst
        if (igst > 0) totalGst += igst
        item.totalGst = totalGst.toString()
        itemMasterEntryDao.insertAll(item)
    }
}