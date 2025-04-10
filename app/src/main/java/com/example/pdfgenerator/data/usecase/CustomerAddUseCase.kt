package com.example.pdfgenerator.data.usecase

import androidx.lifecycle.ViewModel
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.data.customer.CutomerRepo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerAddUseCase @Inject constructor(val customerRepo: CutomerRepo) {
    suspend operator fun invoke(customer: Customer):Flow<Boolean> = withContext(Dispatchers.IO) {
        customerRepo.saveCustomerData(customer)
            channelFlow { send(true) }
    }
}