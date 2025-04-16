package com.example.pdfgenerator.data.usecase

import androidx.lifecycle.ViewModel
import com.example.pdfgenerator.data.customer.BranchRepo
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.data.customer.CutomerRepo
import com.example.pdfgenerator.data.customer.ItemMasterEntryRepo
import com.example.pdfgenerator.data.customer.ItemsMasterEntry
import com.example.pdfgenerator.data.customer.Profile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemMasterAddUseCase @Inject constructor(val itemMasterEntryRepo: ItemMasterEntryRepo) {
    suspend operator fun invoke(branch: ItemsMasterEntry): Flow<Boolean> =
        withContext(Dispatchers.IO) {
            itemMasterEntryRepo.saveCustomerData(branch)
            channelFlow { send(true) }
        }
}