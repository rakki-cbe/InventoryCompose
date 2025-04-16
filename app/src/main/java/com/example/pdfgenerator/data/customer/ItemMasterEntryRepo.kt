package com.example.pdfgenerator.data.customer

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItemMasterEntryRepo @Inject constructor(private val itemMasterEntryDao: ItemMasterEntryDao) {
    fun getAllBranch(): List<ItemsMasterEntry> {
        return itemMasterEntryDao.getAll()
    }

    fun saveCustomerData(item: ItemsMasterEntry) {
        itemMasterEntryDao.insertAll(item)
    }
}