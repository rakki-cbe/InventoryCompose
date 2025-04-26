package rakki.sme.invoice.data.repository

import rakki.sme.invoice.data.dao.BranchDao
import rakki.sme.invoice.data.model.Profile
import javax.inject.Inject

class BranchRepo @Inject constructor(private val customerDao: BranchDao) {
    fun getAllBranch(): List<Profile> {
        return customerDao.getAll()
    }

    fun saveCustomerData(branch: Profile) {
        customerDao.insertAll(branch)
    }
}