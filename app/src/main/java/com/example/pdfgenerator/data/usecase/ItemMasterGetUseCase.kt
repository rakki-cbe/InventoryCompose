package com.example.pdfgenerator.data.usecase

import com.example.pdfgenerator.data.model.ItemsMasterEntry
import com.example.pdfgenerator.data.repository.ItemMasterEntryRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ItemMasterGetUseCase @Inject constructor(val itemMasterEntryRepo: ItemMasterEntryRepo) {

    suspend operator fun invoke(): List<ItemsMasterEntry> = withContext(Dispatchers.IO) {
        itemMasterEntryRepo.getAllBranch()
    }


}