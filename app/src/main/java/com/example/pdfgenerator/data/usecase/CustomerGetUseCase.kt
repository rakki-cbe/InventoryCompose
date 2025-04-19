package com.example.pdfgenerator.data.usecase

import com.example.pdfgenerator.data.model.Customer
import com.example.pdfgenerator.data.repository.CutomerRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerGetUseCase @Inject constructor(val customerRepo: CutomerRepo) {

    suspend operator fun invoke(): List<Customer> = withContext(Dispatchers.IO) {
        customerRepo.getAllCustomer()
    }


}