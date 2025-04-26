package rakki.sme.invoice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rakki.sme.invoice.ui.UseCaseResult
import javax.inject.Inject

@HiltViewModel
class BillerEntryViewModel @Inject constructor(

):ViewModel() {
    private val _result = MutableStateFlow(UseCaseResult())
    val result = _result.asStateFlow()
    private val _seletedBranch: MutableStateFlow<Int> = MutableStateFlow(-1)

    fun setSelectedBranch(branchCode: Int) {
        viewModelScope.launch {
            _seletedBranch.emit(branchCode)
        }
    }
}