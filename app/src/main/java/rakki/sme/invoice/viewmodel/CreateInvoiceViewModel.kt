package rakki.sme.invoice.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rakki.sme.invoice.data.RESUTLT_USECASE_SUCCESS
import rakki.sme.invoice.data.model.Customer
import rakki.sme.invoice.data.model.Inventory
import rakki.sme.invoice.data.model.ItemsInventory
import rakki.sme.invoice.data.model.ItemsMasterEntry
import rakki.sme.invoice.data.model.Profile
import rakki.sme.invoice.data.usecase.InvoiceAddUseCase
import rakki.sme.invoice.domain.InventoryDomainData
import rakki.sme.invoice.domain.getInventoryDbData
import rakki.sme.invoice.domain.getTotalAmount
import rakki.sme.invoice.extension.convertToDouble
import rakki.sme.invoice.extension.convertToInt
import rakki.sme.invoice.ui.Response
import rakki.sme.invoice.ui.pdfgenerator.createPDF
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class CreateInvoiceViewModel @Inject constructor(private val invoiceAddUseCase: InvoiceAddUseCase) :
    ViewModel() {

    private val _branchListUiState = MutableStateFlow(listOf<Profile>())
    val branchListUiState: StateFlow<List<Profile>> = _branchListUiState.asStateFlow()

    private val _customerListUiState = MutableStateFlow(listOf<Customer>())
    val customerListUiState: StateFlow<List<Customer>> = _customerListUiState.asStateFlow()


    private val _itemMasterListUiState = MutableStateFlow(listOf<ItemsMasterEntry>())
    val itemMasterListUiState: StateFlow<List<ItemsMasterEntry>> =
        _itemMasterListUiState.asStateFlow()

    private val _itemTempInvntoryItem = MutableStateFlow(listOf<InventoryDomainData>())
    val itemTempInvntoryItem: StateFlow<List<InventoryDomainData>> =
        _itemTempInvntoryItem.asStateFlow()


    private val _result = MutableStateFlow(Response<String>())
    val result = _result.asStateFlow()

    fun getAllBranch() {
        viewModelScope.launch {
            _branchListUiState.value = invoiceAddUseCase.getBranch()
        }
    }

    fun getCustomer() {
        viewModelScope.launch {
            _customerListUiState.value = invoiceAddUseCase.getCustomer()
        }
    }

    fun getItem() {
        viewModelScope.launch {
            _itemMasterListUiState.value = invoiceAddUseCase.getItemMaterEntry()
        }
    }

    fun clearResultData() {
        _result.value = Response<String>()
    }

    fun saveItemMasterData(
        inventoryDomain: List<InventoryDomainData>,
        profileId: Long,
        customerId: Long,
        context: Context
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val list: List<ItemsInventory> = inventoryDomain.getInventoryDbData()
            val totalAmount = inventoryDomain.getTotalAmount()
            val totalDiscount = 0
            val totalPaidAmount = totalAmount - totalDiscount
            val date = getCurrentDateAndTime()

            val id = invoiceAddUseCase.saveInventroy(
                Inventory(
                    customerId, profileId, totalAmount.toString(),
                    totalDiscount.toString(), totalPaidAmount.toString(), date
                ), list
            )
            id.let {
                val filePath = createPDFForInvoice(context, id)
                _result.value = Response<String>().apply {
                    this.data = filePath
                    this.resultCode = RESUTLT_USECASE_SUCCESS
                }
            }
        }
    }


    suspend fun createPDFForInvoice(context: Context, billerID: Long): String {
        val data = invoiceAddUseCase.getInvoiceDataForPrinter(billerID)
        val lineItem = invoiceAddUseCase.getInvoiceLineItemDataForPrinter(billerID)
        return createPDF(context, data, lineItem)
    }

    fun addTempInventoryItem(item: InventoryDomainData) {
        val list: MutableList<InventoryDomainData> = mutableListOf()
        list.addAll(itemTempInvntoryItem.value)
        item.totalGstPerecent = item.item?.totalGst.convertToDouble(0.0)
        item.totalAmountWithoutGst = calculateTotalAmount(item)
        item.totalGstAmount =
            calculateTotalGstAmount(item.totalGstPerecent, item.totalAmountWithoutGst)
        item.totalAmountWithGstPrice = (item.totalAmountWithoutGst + item.totalGstAmount).toString()
        list.add(item)
        _itemTempInvntoryItem.value = list.toList()
    }

    private fun calculateTotalGstAmount(
        totalGstPercent: Double,
        totalAmount: Double
    ): Double {
        return if (totalGstPercent > 0)
            return ((totalGstPercent / 100) * totalAmount)
        else 0.0
    }

    private fun calculateTotalAmount(item: InventoryDomainData): Double {
        val quty = item.numberOfItem.convertToInt(0)
        val unitPrice = item.unitPrice.convertToDouble(0.0)
        val discount = item.discount.convertToDouble(0.0)
        return (quty * (unitPrice - discount))

    }

    fun getCurrentDateAndTime(): String {
        val c: Date = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val formattedDate: String = simpleDateFormat.format(c)
        return formattedDate
    }
}

