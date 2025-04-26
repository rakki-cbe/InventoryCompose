package com.example.pdfgenerator.di

import com.example.pdfgenerator.data.network.ProfileService
import com.example.pdfgenerator.data.network.usecase.BranchGetNetworkUseCase
import com.example.pdfgenerator.data.repository.BranchRepo
import com.example.pdfgenerator.data.repository.CutomerRepo
import com.example.pdfgenerator.data.repository.InvoiceItemEntryRepo
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


@Module
@InstallIn(ActivityComponent::class)
object UsecaseModule {

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
        invoiceItemEntryRepo: InvoiceItemEntryRepo
    ) = InvoiceAddUseCase(
        branchRepo, customerRepo,
        itemMasterEntryRepo, invoiceRepo, invoiceItemEntryRepo
    )


    @Provides
    fun getBranchNetworkUseCase(profileService: ProfileService) =
        BranchGetNetworkUseCase(profileService)

}