package com.example.pdfgenerator.data.network.usecase

import com.example.pdfgenerator.data.model.Profile
import com.example.pdfgenerator.data.network.ProfileService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BranchAddNetworkUseCase @Inject constructor(val branchRepo: ProfileService) {
    suspend operator fun invoke(profile: Profile): Profile = withContext(Dispatchers.IO) {
        branchRepo.saveProfile(profile)
    }


}