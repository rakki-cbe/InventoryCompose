package rakki.sme.invoice.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import rakki.sme.invoice.data.model.Inventory
import rakki.sme.invoice.data.model.InvoiceLineItemForPrint
import rakki.sme.invoice.data.model.InvoiceUserDisplayData

@Dao
interface InvoiceDao {
    @Query("SELECT * FROM inventory")
    fun getAll(): List<Inventory>

    @Query("SELECT * FROM inventory WHERE billId IN (:billerId)")
    fun loadAllByIds(billerId: IntArray): List<Inventory>

    @Query("SELECT * FROM inventory WHERE date LIKE :date")
    fun findByName(date: String): Inventory

    @Insert
    fun insert(inventory: Inventory): Long

    @Query("SELECT billId FROM inventory WHERE rowid = :rowId")
    fun getBillerId(rowId: Long): Long

    @Delete
    fun delete(inventory: Inventory)

    @Query(
        "SELECT " + " inventory.*, " +
                "Profile.*," +
                "customer.company_name as cust_company_name," +
                "customer.address as cust_address," +
                "customer.phone_number as cust_phone_number," +
                "customer.gst as cust_gst" +
                " from inventory,Profile,customer where inventory.billId = :billerId and " +
                "Profile.profileId = inventory.profileId and customer.custId = inventory.customerId"
    )
    fun getInvoiceDataForPrint(billerId: Long): InvoiceUserDisplayData

    @Query(
        "SELECT itemsmasterentry.*, " +
                "itemsinventory.*" +
                " from itemsinventory,itemsmasterentry where ItemsInventory.billId = :billerId and " +
                "itemsmasterentry.itemId = itemsinventory.itemId"
    )
    fun getInvoiceLineItemForPrint(billerId: Long): List<InvoiceLineItemForPrint>
}