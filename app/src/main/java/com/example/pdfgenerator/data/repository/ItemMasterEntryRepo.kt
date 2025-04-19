package com.example.pdfgenerator.data.repository

import com.example.pdfgenerator.data.dao.ItemMasterEntryDao
import com.example.pdfgenerator.data.model.ItemsMasterEntry
import javax.inject.Inject

class ItemMasterEntryRepo @Inject constructor(private val itemMasterEntryDao: ItemMasterEntryDao) {
    fun getAllBranch(): List<ItemsMasterEntry> {
        return itemMasterEntryDao.getAll()
    }

    fun saveCustomerData(item: ItemsMasterEntry) {
        itemMasterEntryDao.insertAll(item)
    }
}