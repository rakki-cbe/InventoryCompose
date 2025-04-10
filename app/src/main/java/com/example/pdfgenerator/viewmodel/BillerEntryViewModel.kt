package com.example.pdfgenerator.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.data.usecase.CustomerAddUseCase
import com.example.pdfgenerator.data.usecase.CustomerGetUseCase
import com.example.pdfgenerator.extension.filterNull
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BillerEntryViewModel @Inject constructor(
    private val customerGetUseCase: CustomerGetUseCase,
    private val customerAddUseCase: CustomerAddUseCase,
):ViewModel() {

    var billerUiState = BillerUiState()
    private val _customerListUiState = MutableStateFlow(listOf<Customer>())
    val customerListUiState: StateFlow<List<Customer>> = _customerListUiState.asStateFlow()
    fun getAllCustomer():Flow<List<Customer>> = channelFlow   {
        send(customerGetUseCase.invoke().first())
    }

    fun setData():Flow<Boolean> = channelFlow   {
            send( customerAddUseCase.invoke(Customer(
                companyName = billerUiState.companyNameUi.value.filterNull(),
                address = billerUiState.addressStateUi.value.filterNull(),
                phoneNumber =billerUiState.phoneNumberStateUi.value.filterNull(),
                gst = billerUiState.gstStateUi.value.filterNull(),
                bankName = billerUiState.bankNameStateUi.value.filterNull(),
                accountNumber = billerUiState.accountNumberStateUi.value.filterNull(),
                ifscCode = billerUiState.ifscStateUi.value.filterNull()
            )).first())
        }

    fun saveCustomerData(result:(result:Boolean)->Unit) {
        viewModelScope.launch(Dispatchers.IO){
            setData().collect{ it ->
                result.invoke(it)
            }
        }
    }

    fun getCustomerData(result:(result:Boolean)->Unit) {
        viewModelScope.launch(Dispatchers.IO){
            getAllCustomer().collect{ it ->
                viewModelScope.launch(Dispatchers.Main) {
                    _customerListUiState.value = it
                }
            }
        }
    }
}
 class BillerUiState{
     val companyNameUi:MutableLiveData<String> = MutableLiveData("")
     val addressStateUi:MutableLiveData<String> = MutableLiveData("")
     val phoneNumberStateUi:MutableLiveData<String> = MutableLiveData("")
     val bankNameStateUi:MutableLiveData<String> = MutableLiveData("")
     val accountNumberStateUi:MutableLiveData<String> = MutableLiveData("")
     val ifscStateUi:MutableLiveData<String> = MutableLiveData("")
     val gstStateUi:MutableLiveData<String> = MutableLiveData("")
     val isClearCalled:MutableLiveData<Boolean> = MutableLiveData(false)
}