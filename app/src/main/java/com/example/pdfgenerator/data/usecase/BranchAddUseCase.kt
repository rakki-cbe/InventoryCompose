package com.example.pdfgenerator.data.usecase

import androidx.lifecycle.ViewModel
import com.example.pdfgenerator.data.customer.BranchRepo
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.data.customer.CutomerRepo
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

class BranchAddUseCase @Inject constructor(val customerRepo: BranchRepo) {
    suspend operator fun invoke(branch: Profile): Flow<Boolean> = withContext(Dispatchers.IO) {
        customerRepo.saveCustomerData(branch)
        channelFlow { send(true) }
    }
}