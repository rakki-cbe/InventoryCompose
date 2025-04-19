package com.example.pdfgenerator.data.repository

import com.example.pdfgenerator.data.dao.BranchDao
import com.example.pdfgenerator.data.model.Profile
import javax.inject.Inject

class BranchRepo @Inject constructor(private val customerDao: BranchDao) {
    fun getAllBranch(): List<Profile> {
        return customerDao.getAll()
    }

    fun saveCustomerData(branch: Profile) {
        customerDao.insertAll(branch)
    }
}