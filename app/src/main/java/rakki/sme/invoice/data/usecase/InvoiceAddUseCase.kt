package rakki.sme.invoice.data.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import rakki.sme.invoice.data.model.Customer
import rakki.sme.invoice.data.model.Inventory
import rakki.sme.invoice.data.model.InvoiceLineItemForPrint
import rakki.sme.invoice.data.model.InvoiceUserDisplayData
import rakki.sme.invoice.data.model.ItemsInventory
import rakki.sme.invoice.data.model.ItemsMasterEntry
import rakki.sme.invoice.data.model.Profile
import rakki.sme.invoice.data.repository.BranchRepo
import rakki.sme.invoice.data.repository.CutomerRepo
import rakki.sme.invoice.data.repository.InvoiceItemEntryRepo
import rakki.sme.invoice.data.repository.InvoiceRepo
import rakki.sme.invoice.data.repository.ItemMasterEntryRepo
import javax.inject.Inject

class InvoiceAddUseCase @Inject constructor(
    val branchRepo: BranchRepo,
    val customerRepo: CutomerRepo,
    val itemMasterEntryRepo: ItemMasterEntryRepo,
    val invoiceRepo: InvoiceRepo,
    val invoiceItemEntryRepo: InvoiceItemEntryRepo
) {


    suspend fun getBranch(): List<Profile> = withContext(Dispatchers.IO) {
        branchRepo.getAllBranch()
    }

    suspend fun getCustomer(): List<Customer> = withContext(Dispatchers.IO) {
        customerRepo.getAllCustomer()
    }

    suspend fun getItemMaterEntry(): List<ItemsMasterEntry> = withContext(Dispatchers.IO) {
        itemMasterEntryRepo.getAllBranch()
    }

    suspend fun saveInventroy(inventory: Inventory, list: List<ItemsInventory>): Long =
        withContext(Dispatchers.IO) {
            val billerId = invoiceRepo.saveInvoiceData(inventory)
            list.forEach {
                it.billerId = billerId
                invoiceItemEntryRepo.saveInvoiceLineItem(it)
            }
            return@withContext billerId
        }

    suspend fun getInvoiceDataForPrinter(billerId: Long): InvoiceUserDisplayData =
        withContext(Dispatchers.IO) {
            val invoiceData = invoiceRepo.getInvoiceDataForPrint(billerId)
            return@withContext invoiceData
        }

    suspend fun getInvoiceLineItemDataForPrinter(billerId: Long): List<InvoiceLineItemForPrint> =
        withContext(Dispatchers.IO) {
            return@withContext invoiceRepo.getInvoiceLineItemForPrint(billerId)
        }
}