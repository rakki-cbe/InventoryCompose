package com.example.pdfgenerator.data.customer

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CutomerRepo @Inject constructor( private val customerDao: CustomerDao) {
    fun getAllCustomer(): Flow<List<Customer>> {
        return customerDao.getAll()
    }

    fun saveCustomerData(customer: Customer){
        customerDao.insertAll(customer)
    }
}