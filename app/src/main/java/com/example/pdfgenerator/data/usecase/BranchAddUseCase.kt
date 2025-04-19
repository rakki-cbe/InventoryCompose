package com.example.pdfgenerator.data.usecase

import com.example.pdfgenerator.data.model.Profile
import com.example.pdfgenerator.data.repository.BranchRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BranchAddUseCase @Inject constructor(val customerRepo: BranchRepo) {
    suspend operator fun invoke(branch: Profile): Flow<Boolean> = withContext(Dispatchers.IO) {
        customerRepo.saveCustomerData(branch)
        channelFlow { send(true) }
    }
}