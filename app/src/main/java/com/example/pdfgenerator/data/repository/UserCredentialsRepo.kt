package com.example.pdfgenerator.data.repository

import com.example.pdfgenerator.data.dao.ActiveUserDao
import com.example.pdfgenerator.data.model.CurrentUserData
import javax.inject.Inject

class UserCredentialsRepo @Inject constructor(private val activeUserDao: ActiveUserDao) {
    fun getAlluser(): List<CurrentUserData> {
        return activeUserDao.getAll()
    }

    fun saveUserData(currentUserData: CurrentUserData) {
        deleteAll()
        activeUserDao.insertAll(currentUserData)
    }

    fun deleteAll() {
        activeUserDao.deleteAll()
    }
}