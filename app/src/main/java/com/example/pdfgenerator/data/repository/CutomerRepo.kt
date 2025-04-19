package com.example.pdfgenerator.data.repository

import com.example.pdfgenerator.data.dao.CustomerDao
import com.example.pdfgenerator.data.model.Customer
import javax.inject.Inject

class CutomerRepo @Inject constructor( private val customerDao: CustomerDao) {
    fun getAllCustomer(): List<Customer> {
        return customerDao.getAll()
    }

    fun saveCustomerData(customer: Customer){
        customerDao.insertAll(customer)
    }
}