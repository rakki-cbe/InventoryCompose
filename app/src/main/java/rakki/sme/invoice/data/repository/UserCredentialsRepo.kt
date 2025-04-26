package rakki.sme.invoice.data.repository

import rakki.sme.invoice.data.dao.ActiveUserDao
import rakki.sme.invoice.data.model.CurrentUserData
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