package com.example.pdfgenerator.data

import com.example.pdfgenerator.data.customer.Inventory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvoiceRepo @Inject constructor(private val invoiceDao: InvoiceDao) {
    fun getAllBranch(): List<Inventory> {
        return invoiceDao.getAll()
    }

    /**8
     * After saving data it returns biller id of saved item which helps to add inventory
     */
    fun saveCustomerData(item: Inventory): Long {
        val rowId = invoiceDao.insert(item)
        return invoiceDao.getBillerId(rowId)
    }
}