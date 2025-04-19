package com.example.pdfgenerator.data.usecase

import com.example.pdfgenerator.data.dao.InvoiceItemEntryDao
import com.example.pdfgenerator.data.model.Customer
import com.example.pdfgenerator.data.model.Inventory
import com.example.pdfgenerator.data.model.ItemsInventory
import com.example.pdfgenerator.data.model.ItemsMasterEntry
import com.example.pdfgenerator.data.model.Profile
import com.example.pdfgenerator.data.repository.BranchRepo
import com.example.pdfgenerator.data.repository.CutomerRepo
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