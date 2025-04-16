package com.example.pdfgenerator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pdfgenerator.UseCaseResult
import com.example.pdfgenerator.data.RESUTLT_USECASE_SUCCESS
import com.example.pdfgenerator.data.customer.ItemsMasterEntry
import com.example.pdfgenerator.data.customer.Profile
import com.example.pdfgenerator.data.usecase.BranchAddUseCase
import com.example.pdfgenerator.data.usecase.BranchGetUseCase
import com.example.pdfgenerator.data.usecase.ItemMasterAddUseCase
import com.example.pdfgenerator.data.usecase.ItemMasterGetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BranchViewModel @Inject constructor(
    private val branchAddUseCase: BranchAddUseCase
) : ViewModel() {


    private val _result = MutableStateFlow(UseCaseResult())
    val result = _result.asStateFlow()

    fun clearResultData() {
        _result.value = UseCaseResult()
    }

    fun saveBranchData(branchDetails: Profile) {
        viewModelScope.launch(Dispatchers.IO) {
            branchAddUseCase.invoke(branchDetails).collect {
                val result = UseCaseResult()
                result.resultCode = 200
                result.isLoading = false
                _result.emit(result)

            }
        }
    }
}