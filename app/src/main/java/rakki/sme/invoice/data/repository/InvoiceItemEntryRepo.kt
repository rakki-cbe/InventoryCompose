package rakki.sme.invoice.data.repository

import rakki.sme.invoice.data.dao.InvoiceItemEntryDao
import rakki.sme.invoice.data.model.ItemsInventory
import javax.inject.Inject

class InvoiceItemEntryRepo @Inject constructor(private val invoiceItemEntryDao: InvoiceItemEntryDao) {
    fun getAllBranch(): List<ItemsInventory> {
        return invoiceItemEntryDao.getAll()
    }

    fun saveInvoiceLineItem(item: ItemsInventory) {
        invoiceItemEntryDao.insertAll(item)
    }
}