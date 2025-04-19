package com.example.pdfgenerator.di

import android.content.Context
import androidx.room.Room
import com.example.pdfgenerator.data.InventoryDB
import com.example.pdfgenerator.data.dao.BranchDao
import com.example.pdfgenerator.data.dao.CustomerDao
import com.example.pdfgenerator.data.dao.InvoiceItemEntryDao
import com.example.pdfgenerator.data.repository.BranchRepo
import com.example.pdfgenerator.data.repository.CutomerRepo
import com.example.pdfgenerator.data.repository.InvoiceRepo
import com.example.pdfgenerator.data.repository.ItemMasterEntryRepo
import com.example.pdfgenerator.data.usecase.BranchAddUseCase
import com.example.pdfgenerator.data.usecase.BranchGetUseCase
import com.example.pdfgenerator.data.usecase.CustomerAddUseCase
import com.example.pdfgenerator.data.usecase.CustomerGetUseCase
import com.example.pdfgenerator.data.usecase.InvoiceAddUseCase
import com.example.pdfgenerator.data.usecase.ItemMasterAddUseCase
import com.example.pdfgenerator.data.usecase.ItemMasterGetUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationLevelDIModule {

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
    fun provideProfileDao(db: InventoryDB) = db.profileDao()

    @Singleton
    @Provides
    fun provideItemMasterEntryDao(db: InventoryDB) = db.itemMasterEntryDao()

    @Singleton
    @Provides
    fun provideInvoiceDao(db: InventoryDB) = db.invoiceDao()

    @Singleton
    @Provides
    fun provideInvoiceItemEntryDao(db: InventoryDB) = db.invoiceItemDao()

}

@Module
@InstallIn(ActivityComponent::class)
object ActivityComponent {
    @Provides
    fun getCustomerRepo(customerDao: CustomerDao) = CutomerRepo(customerDao)

    @Provides
    fun getBranchRepo(customerDao: BranchDao) = BranchRepo(customerDao)

    @Provides
    fun customerSaveUseCase(customerRepo: CutomerRepo): CustomerAddUseCase =
        CustomerAddUseCase(customerRepo)

    @Provides
    fun getCustomerUseCase(customerRepo: CutomerRepo): CustomerGetUseCase =
        CustomerGetUseCase(customerRepo)

    @Provides
    fun brachSaveUseCase(branchRepo: BranchRepo): BranchAddUseCase = BranchAddUseCase(branchRepo)

    @Provides
    fun getBranchUseCase(branchRepo: BranchRepo): BranchGetUseCase = BranchGetUseCase(branchRepo)

    @Provides
    fun itemSaveUseCase(itemMasterEntryRepo: ItemMasterEntryRepo) =
        ItemMasterAddUseCase(itemMasterEntryRepo)

    @Provides
    fun getItemUseCase(itemMasterEntryRepo: ItemMasterEntryRepo) =
        ItemMasterGetUseCase(itemMasterEntryRepo)

    @Provides
    fun addInvoice(
        itemMasterEntryRepo: ItemMasterEntryRepo,
        branchRepo: BranchRepo,
        customerRepo: CutomerRepo,
        invoiceRepo: InvoiceRepo,
        itemEntryDao: InvoiceItemEntryDao
    ) = InvoiceAddUseCase(
        branchRepo, customerRepo,
        itemMasterEntryRepo, invoiceRepo, itemEntryDao
    )

}