package rakki.sme.invoice.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import rakki.sme.invoice.data.network.ProfileService
import rakki.sme.invoice.data.network.usecase.BranchGetNetworkUseCase
import rakki.sme.invoice.data.repository.BranchRepo
import rakki.sme.invoice.data.repository.CutomerRepo
import rakki.sme.invoice.data.repository.InvoiceItemEntryRepo
import rakki.sme.invoice.data.repository.InvoiceRepo
import rakki.sme.invoice.data.repository.ItemMasterEntryRepo
import rakki.sme.invoice.data.usecase.BranchAddUseCase
import rakki.sme.invoice.data.usecase.BranchGetUseCase
import rakki.sme.invoice.data.usecase.CustomerAddUseCase
import rakki.sme.invoice.data.usecase.CustomerGetUseCase
import rakki.sme.invoice.data.usecase.InvoiceAddUseCase
import rakki.sme.invoice.data.usecase.ItemMasterAddUseCase
import rakki.sme.invoice.data.usecase.ItemMasterGetUseCase


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