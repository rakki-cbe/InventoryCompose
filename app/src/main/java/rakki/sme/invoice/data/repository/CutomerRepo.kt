package rakki.sme.invoice.data.repository

import rakki.sme.invoice.data.dao.CustomerDao
import rakki.sme.invoice.data.model.Customer
import javax.inject.Inject

class CutomerRepo @Inject constructor( private val customerDao: CustomerDao) {
    fun getAllCustomer(): List<Customer> {
        return customerDao.getAll()
    }

    fun saveCustomerData(customer: Customer){
        if (customer.custId > 0)
            customerDao.update(customer)
        else
            customerDao.insertAll(customer)
    }

    fun deleteRecord(customer: Customer) {
        customerDao.delete(customer)
    }
}