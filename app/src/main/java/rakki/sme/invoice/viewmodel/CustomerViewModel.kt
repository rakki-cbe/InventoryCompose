package rakki.sme.invoice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rakki.sme.invoice.data.RESUTLT_USECASE_SUCCESS
import rakki.sme.invoice.data.model.Customer
import rakki.sme.invoice.data.usecase.CustomerAddUseCase
import rakki.sme.invoice.data.usecase.CustomerDeleteUseCase
import rakki.sme.invoice.data.usecase.CustomerGetUseCase
import rakki.sme.invoice.ui.UseCaseResult
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val customerAddUseCase: CustomerAddUseCase,
    private val getUseCase: CustomerGetUseCase,
    private val delete: CustomerDeleteUseCase,
) : ViewModel() {


    private val _result = MutableStateFlow(UseCaseResult())
    val result = _result.asStateFlow()

    private val _customerList = MutableStateFlow<List<Customer>>(listOf())
    val customerList = _customerList.asStateFlow()

    private val _selectedCustomerForDelete = MutableStateFlow<Customer?>(null)
    val selectedCustomerForDelete = _selectedCustomerForDelete.asStateFlow()

    private val _selectedCustomerForEdit = MutableStateFlow<Customer?>(null)
    val selectedCustomerForEdit = _selectedCustomerForEdit.asStateFlow()

    fun clearResultData() {
        _result.value = UseCaseResult()
    }


    fun saveCustomerData(customer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            customerAddUseCase.invoke(customer).collect {
                resetSelectedCustomerForEdit()
                resetSelectedCustomerForDelete()
                _result.value = UseCaseResult().apply {
                    resultCode = RESUTLT_USECASE_SUCCESS
                    isLoading = false
                }
            }
        }
    }

    fun getAllCustomerList() {
        viewModelScope.launch(Dispatchers.IO) {
            _customerList.value = getUseCase.invoke()
        }
    }

    fun deleteRecord(customer: Customer) {
        viewModelScope.launch(Dispatchers.Default) {
            delete.invoke(customer)
            resetSelectedCustomerForEdit()
            resetSelectedCustomerForDelete()
            getAllCustomerList()
        }
    }

    fun selectedCustomerForDelete(customer: Customer) {
        _selectedCustomerForDelete.value = customer
    }

    fun selectedCustomerForEdit(customer: Customer) {
        _selectedCustomerForEdit.value = customer
    }

    fun resetSelectedCustomerForEdit() {
        _selectedCustomerForEdit.value = null
    }

    fun resetSelectedCustomerForDelete() {
        _selectedCustomerForDelete.value = null
    }


}