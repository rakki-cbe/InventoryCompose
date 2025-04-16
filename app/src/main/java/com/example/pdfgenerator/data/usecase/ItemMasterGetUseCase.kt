package com.example.pdfgenerator.data.usecase

import com.example.pdfgenerator.data.customer.BranchRepo
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.data.customer.CutomerRepo
import com.example.pdfgenerator.data.customer.ItemMasterEntryRepo
import com.example.pdfgenerator.data.customer.ItemsMasterEntry
import com.example.pdfgenerator.data.customer.Profile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemMasterGetUseCase @Inject constructor(val itemMasterEntryRepo: ItemMasterEntryRepo) {

    suspend operator fun invoke(): List<ItemsMasterEntry> = withContext(Dispatchers.IO) {
        itemMasterEntryRepo.getAllBranch()
    }


}