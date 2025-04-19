package com.example.pdfgenerator.data.usecase

import com.example.pdfgenerator.data.model.ItemsMasterEntry
import com.example.pdfgenerator.data.repository.ItemMasterEntryRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemMasterAddUseCase @Inject constructor(val itemMasterEntryRepo: ItemMasterEntryRepo) {
    suspend operator fun invoke(branch: ItemsMasterEntry): Flow<Boolean> =
        withContext(Dispatchers.IO) {
            itemMasterEntryRepo.saveCustomerData(branch)
            channelFlow { send(true) }
        }
}