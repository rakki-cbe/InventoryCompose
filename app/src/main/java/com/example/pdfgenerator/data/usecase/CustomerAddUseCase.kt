package com.example.pdfgenerator.data.usecase

import com.example.pdfgenerator.data.model.Customer
import com.example.pdfgenerator.data.repository.CutomerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerAddUseCase @Inject constructor(val customerRepo: CutomerRepo) {
    suspend operator fun invoke(customer: Customer):Flow<Boolean> = withContext(Dispatchers.IO) {
        customerRepo.saveCustomerData(customer)
            channelFlow { send(true) }
    }
}