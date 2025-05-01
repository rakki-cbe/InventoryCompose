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
import rakki.sme.invoice.R
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
import java.math.BigDecimal
import java.math.RoundingMode
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

    private val _selectedBranchId = MutableStateFlow<Long>(0)
    val selectedBranchId: StateFlow<Long> = _selectedBranchId.asStateFlow()

    private val _selectedCustomerId = MutableStateFlow<Long>(0)
    val selectedCustomerId: StateFlow<Long> = _selectedCustomerId.asStateFlow()

    private val _selectedItemId = MutableStateFlow<Long>(0)
    val selectedItemId: StateFlow<Long> = _selectedItemId.asStateFlow()


    private val _result = MutableStateFlow(Response<String>())
    val result = _result.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage = _errorMessage.asStateFlow()

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
        if (profileId <= 0) {
            _errorMessage.value = context.getString(R.string.error_selected_branch)
        } else if (customerId <= 0) {
            _errorMessage.value = context.getString(R.string.error_selected_customer)
        } else if (inventoryDomain.isEmpty())
            _errorMessage.value = context.getString(R.string.error_add_inventory_info)
        else {
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
    }


    suspend fun createPDFForInvoice(context: Context, billerID: Long): String {
        val data = invoiceAddUseCase.getInvoiceDataForPrinter(billerID)
        val lineItem = invoiceAddUseCase.getInvoiceLineItemDataForPrinter(billerID)
        return createPDF(context, data, lineItem)
    }

    fun addTempInventoryItem(context: Context, item: InventoryDomainData) {
        if (checkIsValidInput(context, item, itemTempInvntoryItem)) {
            val list: MutableList<InventoryDomainData> = mutableListOf()
            list.addAll(itemTempInvntoryItem.value)
            item.totalGstPerecent = item.item?.totalGst.convertToDouble(0.0)
            item.totalAmountWithoutGst = calculateTotalAmount(item)
            item.totalGstAmount =
                calculateTotalGstAmount(item.totalGstPerecent, item.totalAmountWithoutGst)
            item.totalAmountWithGstPrice =
                (item.totalAmountWithoutGst + item.totalGstAmount).toString()
            list.add(item)
            _itemTempInvntoryItem.value = list.toList()
        }
    }

    private fun checkIsValidInput(
        context: Context, item: InventoryDomainData,
        itemTempInvntoryItem: StateFlow<List<InventoryDomainData>>
    ): Boolean {
        return if (itemTempInvntoryItem.value.size >= 6) {
            _errorMessage.value = context.getString(R.string.error_cannot_add_more)
            false
        } else if (item.item == null) {
            _errorMessage.value = context.getString(R.string.error_product_not_selected)
            false
        } else true
    }

    private fun calculateTotalGstAmount(
        totalGstPercent: Double,
        totalAmount: Double
    ): Double {
        return if (totalGstPercent > 0)
            return BigDecimal(((totalGstPercent / 100) * totalAmount))
                .setScale(2, RoundingMode.HALF_EVEN).toDouble()
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

    fun resetErrorMessage() {
        _errorMessage.value = ""
    }

    fun setSelectedProfile(it: Long) {
        _selectedBranchId.value = it
    }

    fun setSelectedCustomer(it: Long) {
        _selectedCustomerId.value = it
    }

    fun setSelectedItem(it: Long) {
        _selectedItemId.value = it
    }

    fun clearAllSelectedItem() {
        _selectedBranchId.value = 0
        _selectedCustomerId.value = 0
        _selectedItemId.value = 0
        _itemTempInvntoryItem.value = listOf()

    }

    fun deleteTempItem(item: InventoryDomainData) {
        _itemTempInvntoryItem.value = _itemTempInvntoryItem.value.filterNot { it == item }
    }


}

