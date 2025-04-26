package rakki.sme.invoice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rakki.sme.invoice.data.model.Profile
import rakki.sme.invoice.data.network.usecase.BranchAddNetworkUseCase
import rakki.sme.invoice.data.network.usecase.BranchGetNetworkUseCase
import rakki.sme.invoice.data.usecase.BranchAddUseCase
import rakki.sme.invoice.ui.UseCaseResult
import javax.inject.Inject

@HiltViewModel
class BranchViewModel @Inject constructor(
    private val branchAddUseCase: BranchAddUseCase,
    private val branchGetNetworkUseCase: BranchGetNetworkUseCase,
    private val branchAddNetworkUseCase: BranchAddNetworkUseCase
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
            // branchAddNetworkUseCase.invoke(branchDetails) // TODO Need to host server
        }
    }
}