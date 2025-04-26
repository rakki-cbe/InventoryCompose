package rakki.sme.invoice.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import rakki.sme.invoice.data.model.Customer

@Dao
interface CustomerDao {
    @Query("SELECT * FROM customer")
    fun getAll(): List<Customer>

    @Query("SELECT * FROM customer WHERE custId IN (:custId)")
    fun loadAllByIds(custId: IntArray): List<Customer>

    @Query("SELECT * FROM customer WHERE company_name LIKE :name")
    fun findByName(name: String): Customer

    @Insert
    fun insertAll(vararg users: Customer)

    @Delete
    fun delete(user: Customer)
}