package com.example.pdfgenerator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdfgenerator.UseCaseResult
import com.example.pdfgenerator.data.RESUTLT_USECASE_SUCCESS
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.data.customer.Inventory
import com.example.pdfgenerator.data.customer.ItemsInventory
import com.example.pdfgenerator.data.customer.ItemsMasterEntry
import com.example.pdfgenerator.data.customer.Profile
import com.example.pdfgenerator.data.usecase.InvoiceAddUseCase
import com.example.pdfgenerator.domain.InventoryDomainData
import com.example.pdfgenerator.domain.getInventoryDbData
import com.example.pdfgenerator.domain.getTotalAmount
import com.example.pdfgenerator.extension.convertToDouble
import com.example.pdfgenerator.extension.convertToInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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


    private val _result = MutableStateFlow(UseCaseResult())
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
        _result.value = UseCaseResult()
    }

    fun saveItemMasterData(
        inventoryDomain: List<InventoryDomainData>,
        profileId: Long,
        customerId: Long
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val list: List<ItemsInventory> = inventoryDomain.getInventoryDbData()
            val totalAmount = inventoryDomain.getTotalAmount()
            val totalDiscount = 0
            val totalPaidAmount = totalAmount - totalDiscount
            val date = Calendar.getInstance().toString()
            invoiceAddUseCase.saveInventroy(
                Inventory(
                    customerId, profileId, totalAmount.toString(),
                    totalDiscount.toString(), totalPaidAmount.toString(), date
                ), list
            )
            _result.value = UseCaseResult().apply {
                resultCode = RESUTLT_USECASE_SUCCESS
                isLoading = false

            }
        }
    }

    fun addTempInventoryItem(item: InventoryDomainData) {
        val list: MutableList<InventoryDomainData> = mutableListOf()
        list.addAll(itemTempInvntoryItem.value)
        item.totalGstPerecent = calculateTotalGstPercent(item)
        item.totalPrice = calculateTotalAmount(item)
        list.add(item)
        _itemTempInvntoryItem.value = list.toList()
    }

    private fun calculateTotalGstPercent(inventory: InventoryDomainData): Double {
        val sgst = inventory.item?.sgst.convertToDouble(0.0)
        val cgst = inventory.item?.sgst.convertToDouble(0.0)
        val igst = inventory.item?.sgst.convertToDouble(0.0)
        return sgst + cgst + igst
    }

    private fun calculateTotalAmount(item: InventoryDomainData): String {
        val quty = item.numberOfItem.convertToInt(0)
        val unitPrice = item.unitPrice.convertToDouble(0.0)
        val discount = item.discount.convertToDouble(0.0)
        val total = (quty * (unitPrice - discount))
        if (item.totalGstPerecent > 0) {
            return (total + ((item.totalGstPerecent / 100) * total)).toString()
        }
        return total.toString()
    }
}

