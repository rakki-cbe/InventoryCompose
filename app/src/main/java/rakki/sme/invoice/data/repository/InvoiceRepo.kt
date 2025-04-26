package rakki.sme.invoice.data.repository

import rakki.sme.invoice.data.dao.InvoiceDao
import rakki.sme.invoice.data.model.Inventory
import rakki.sme.invoice.data.model.InvoiceLineItemForPrint
import rakki.sme.invoice.data.model.InvoiceUserDisplayData
import javax.inject.Inject

class InvoiceRepo @Inject constructor(private val invoiceDao: InvoiceDao) {

    /**8
     * After saving data it returns biller id of saved item which helps to add inventory
     */
    fun saveInvoiceData(item: Inventory): Long {
        val rowId = invoiceDao.insert(item)
        return invoiceDao.getBillerId(rowId)
    }

    fun getInvoiceDataForPrint(billerID: Long): InvoiceUserDisplayData {
        return invoiceDao.getInvoiceDataForPrint(billerID)
    }

    fun getInvoiceLineItemForPrint(billerID: Long): List<InvoiceLineItemForPrint> {
        return invoiceDao.getInvoiceLineItemForPrint(billerID)
    }
}