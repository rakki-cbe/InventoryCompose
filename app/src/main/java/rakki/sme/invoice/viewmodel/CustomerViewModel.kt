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
import rakki.sme.invoice.ui.UseCaseResult
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