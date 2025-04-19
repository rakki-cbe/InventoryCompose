package com.example.pdfgenerator.di

import android.content.Context
import androidx.room.Room
import com.example.pdfgenerator.data.InventoryDB
import com.example.pdfgenerator.data.dao.ActiveUserDao
import com.example.pdfgenerator.data.dao.BranchDao
import com.example.pdfgenerator.data.dao.CustomerDao
import com.example.pdfgenerator.data.network.UserCredentials
import com.example.pdfgenerator.data.repository.BranchRepo
import com.example.pdfgenerator.data.repository.CutomerRepo
import com.example.pdfgenerator.data.repository.UserCredentialsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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