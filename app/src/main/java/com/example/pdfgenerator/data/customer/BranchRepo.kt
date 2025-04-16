package com.example.pdfgenerator.data.customer

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BranchRepo @Inject constructor(private val customerDao: BranchDao) {
    fun getAllBranch(): List<Profile> {
        return customerDao.getAll()
    }

    fun saveCustomerData(branch: Profile) {
        customerDao.insertAll(branch)
    }
}