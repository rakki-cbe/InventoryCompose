package com.example.pdfgenerator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdfgenerator.UseCaseResult
import com.example.pdfgenerator.data.RESUTLT_USECASE_SUCCESS
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.data.customer.ItemsMasterEntry
import com.example.pdfgenerator.data.customer.Profile
import com.example.pdfgenerator.data.usecase.BranchAddUseCase
import com.example.pdfgenerator.data.usecase.BranchGetUseCase
import com.example.pdfgenerator.data.usecase.CustomerAddUseCase
import com.example.pdfgenerator.data.usecase.CustomerGetUseCase
import com.example.pdfgenerator.data.usecase.ItemMasterAddUseCase
import com.example.pdfgenerator.data.usecase.ItemMasterGetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    private val customerAddUseCase: CustomerAddUseCase,
) : ViewModel() {


    private val _result = MutableStateFlow(UseCaseResult())
    val result = _result.asStateFlow()

    fun clearResultData() {
        _result.value = UseCaseResult()
    }


    fun saveCustomerData(customer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            customerAddUseCase.invoke(customer).collect {
                _result.value = UseCaseResult().apply {
                    resultCode = RESUTLT_USECASE_SUCCESS
                    isLoading = false
                }
            }
        }
    }


}