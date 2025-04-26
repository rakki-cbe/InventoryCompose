package rakki.sme.invoice.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import rakki.sme.invoice.data.model.ItemsMasterEntry

@Dao
interface ItemMasterEntryDao {
    @Query("SELECT * FROM itemsmasterentry")
    fun getAll(): List<ItemsMasterEntry>

    @Query("SELECT * FROM itemsmasterentry WHERE itemId IN (:custId)")
    fun loadAllByIds(custId: IntArray): List<ItemsMasterEntry>

    @Query("SELECT * FROM itemsmasterentry WHERE name LIKE :name")
    fun findByName(name: String): ItemsMasterEntry

    @Insert
    fun insertAll(vararg itemsMasterEntry: ItemsMasterEntry)

    @Delete
    fun delete(itemsMasterEntry: ItemsMasterEntry)
}