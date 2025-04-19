package com.example.pdfgenerator.data.usecase

import com.example.pdfgenerator.data.model.Profile
import com.example.pdfgenerator.data.repository.BranchRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BranchGetUseCase @Inject constructor(val branchRepo: BranchRepo) {

    suspend operator fun invoke(): List<Profile> = withContext(Dispatchers.IO) {
        branchRepo.getAllBranch()
    }


}