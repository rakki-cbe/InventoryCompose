package rakki.sme.invoice.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rakki.sme.invoice.data.InventoryDB
import rakki.sme.invoice.data.dao.ActiveUserDao
import rakki.sme.invoice.data.dao.BranchDao
import rakki.sme.invoice.data.dao.CustomerDao
import rakki.sme.invoice.data.network.UserCredentials
import rakki.sme.invoice.data.repository.BranchRepo
import rakki.sme.invoice.data.repository.CutomerRepo
import rakki.sme.invoice.data.repository.UserCredentialsRepo
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): InventoryDB {
        return Room.databaseBuilder(
            appContext,
            InventoryDB::class.java,
            "room_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: InventoryDB) = db.customerDao()


    @Singleton
    @Provides
    fun provideActiveUserData() = UserCredentials()

    @Singleton
    @Provides
    fun provideProfileDao(db: InventoryDB) = db.profileDao()

    @Singleton
    @Provides
    fun provideItemMasterEntryDao(db: InventoryDB) = db.itemMasterEntryDao()

    @Singleton
    @Provides
    fun provideCredentialsDao(db: InventoryDB) = db.activeUserDao()

    @Singleton
    @Provides
    fun provideInvoiceDao(db: InventoryDB) = db.invoiceDao()

    @Singleton
    @Provides
    fun provideInvoiceItemEntryDao(db: InventoryDB) = db.invoiceItemDao()
}

@Module
@InstallIn(ActivityComponent::class)
object dataBaseRepoComponent {
    @Provides
    fun getCustomerRepo(customerDao: CustomerDao) = CutomerRepo(customerDao)

    @Provides
    fun getBranchRepo(customerDao: BranchDao) = BranchRepo(customerDao)

    @Provides
    fun getActiveUserRepo(activeUserDao: ActiveUserDao) =
        UserCredentialsRepo(activeUserDao)
}