package com.example.pdfgenerator.data.usecase

import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.data.customer.CutomerRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerGetUseCase @Inject constructor(val customerRepo: CutomerRepo) {

    suspend operator fun invoke():Flow<List<Customer>> = withContext(Dispatchers.IO) {
        customerRepo.getAllCustomer()
    }


}