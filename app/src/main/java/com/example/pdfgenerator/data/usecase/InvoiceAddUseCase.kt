package com.example.pdfgenerator.data.usecase

import com.example.pdfgenerator.data.InvoiceItemEntryDao
import com.example.pdfgenerator.data.InvoiceRepo
import com.example.pdfgenerator.data.customer.BranchRepo
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.data.customer.CutomerRepo
import com.example.pdfgenerator.data.customer.Inventory
import com.example.pdfgenerator.data.customer.ItemMasterEntryRepo
import com.example.pdfgenerator.data.customer.ItemsInventory
import com.example.pdfgenerator.data.customer.ItemsMasterEntry
import com.example.pdfgenerator.data.customer.Profile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InvoiceAddUseCase @Inject constructor(
    val branchRepo: BranchRepo,
    val customerRepo: CutomerRepo,
    val itemMasterEntryRepo: ItemMasterEntryRepo,
    val invoiceRepo: InvoiceRepo,
    val invoiceItemEntryDao: InvoiceItemEntryDao
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

    suspend fun saveInventroy(inventory: Inventory, list: List<ItemsInventory>): Boolean =
        withContext(Dispatchers.IO) {
            val billerId = invoiceRepo.saveCustomerData(inventory)
            list.forEach {
                it.billerId = billerId
                invoiceItemEntryDao.insertAll(it)
            }
            return@withContext true
        }


}