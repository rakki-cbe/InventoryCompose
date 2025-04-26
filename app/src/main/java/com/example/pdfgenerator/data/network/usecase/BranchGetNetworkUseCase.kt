package com.example.pdfgenerator.data.network.usecase

import com.example.pdfgenerator.data.model.Profile
import com.example.pdfgenerator.data.network.ProfileService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BranchGetNetworkUseCase @Inject constructor(val branchRepo: ProfileService) {
    suspend operator fun invoke(): Profile = withContext(Dispatchers.IO) {
        branchRepo.getProfileForCompany("test")
    }
}