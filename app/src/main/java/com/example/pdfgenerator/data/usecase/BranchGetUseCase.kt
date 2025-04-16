package com.example.pdfgenerator.data.usecase

import com.example.pdfgenerator.data.customer.BranchRepo
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.data.customer.CutomerRepo
import com.example.pdfgenerator.data.customer.Profile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BranchGetUseCase @Inject constructor(val branchRepo: BranchRepo) {

    suspend operator fun invoke(): List<Profile> = withContext(Dispatchers.IO) {
        branchRepo.getAllBranch()
    }


}