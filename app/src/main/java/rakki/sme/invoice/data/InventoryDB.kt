package rakki.sme.invoice.data

import androidx.room.Database
import androidx.room.RoomDatabase
import rakki.sme.invoice.data.dao.ActiveUserDao
import rakki.sme.invoice.data.dao.BranchDao
import rakki.sme.invoice.data.dao.CustomerDao
import rakki.sme.invoice.data.dao.InvoiceDao
import rakki.sme.invoice.data.dao.InvoiceItemEntryDao
import rakki.sme.invoice.data.dao.ItemMasterEntryDao
import rakki.sme.invoice.data.model.CurrentUserData
import rakki.sme.invoice.data.model.Customer
import rakki.sme.invoice.data.model.Inventory
import rakki.sme.invoice.data.model.ItemsInventory
import rakki.sme.invoice.data.model.ItemsMasterEntry
import rakki.sme.invoice.data.model.Profile

@Database(
    entities = [Profile::class, Customer::class, ItemsMasterEntry::class, Inventory::class,
        ItemsInventory::class, CurrentUserData::class], version = 3
)
abstract class InventoryDB : RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun profileDao(): BranchDao
    abstract fun itemMasterEntryDao(): ItemMasterEntryDao
    abstract fun invoiceDao(): InvoiceDao
    abstract fun invoiceItemDao(): InvoiceItemEntryDao
    abstract fun activeUserDao(): ActiveUserDao

}