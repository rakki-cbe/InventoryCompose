package com.example.pdfgenerator.di

import android.content.Context
import androidx.room.Room
import com.example.pdfgenerator.data.InventoryDB
import com.example.pdfgenerator.data.customer.CustomerDao
import com.example.pdfgenerator.data.customer.CutomerRepo
import com.example.pdfgenerator.data.usecase.CustomerAddUseCase
import com.example.pdfgenerator.data.usecase.CustomerGetUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
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
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserDao(db: InventoryDB) = db.customerDao()

}

@Module
@InstallIn(ActivityComponent::class)
object ActivityComponent {
    @Provides
    fun getCustomerRepo(customerDao: CustomerDao) = CutomerRepo(customerDao)

    @Provides
    fun customerSaveUseCase(customerRepo:CutomerRepo):CustomerAddUseCase = CustomerAddUseCase(customerRepo)

    @Provides
    fun getCustomerUseCase(customerRepo:CutomerRepo):CustomerGetUseCase = CustomerGetUseCase(customerRepo)
}