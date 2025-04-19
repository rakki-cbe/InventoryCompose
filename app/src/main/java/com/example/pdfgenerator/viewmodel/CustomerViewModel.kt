package com.example.pdfgenerator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdfgenerator.data.RESUTLT_USECASE_SUCCESS
import com.example.pdfgenerator.data.model.Customer
import com.example.pdfgenerator.data.usecase.CustomerAddUseCase
import com.example.pdfgenerator.ui.UseCaseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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