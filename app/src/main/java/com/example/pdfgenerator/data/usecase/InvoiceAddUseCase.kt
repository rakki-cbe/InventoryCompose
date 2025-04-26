package com.example.pdfgenerator.data.usecase

import com.example.pdfgenerator.data.model.Customer
import com.example.pdfgenerator.data.model.Inventory
import com.example.pdfgenerator.data.model.InvoiceLineItemForPrint
import com.example.pdfgenerator.data.model.InvoiceUserDisplayData
import com.example.pdfgenerator.data.model.ItemsInventory
import com.example.pdfgenerator.data.model.ItemsMasterEntry
import com.example.pdfgenerator.data.model.Profile
import com.example.pdfgenerator.data.repository.BranchRepo
import com.example.pdfgenerator.data.repository.CutomerRepo
import com.example.pdfgenerator.data.repository.InvoiceItemEntryRepo
import com.example.pdfgenerator.data.repository.InvoiceRepo
import com.example.pdfgenerator.data.repository.ItemMasterEntryRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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