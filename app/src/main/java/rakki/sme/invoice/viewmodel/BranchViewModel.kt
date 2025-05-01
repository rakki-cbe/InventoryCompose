package rakki.sme.invoice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rakki.sme.invoice.data.model.Profile
import rakki.sme.invoice.data.usecase.BranchAddUseCase
import rakki.sme.invoice.data.usecase.BranchDeleteUseCase
import rakki.sme.invoice.data.usecase.BranchGetUseCase
import rakki.sme.invoice.ui.UseCaseResult
import javax.inject.Inject

@HiltViewModel
class BranchViewModel @Inject constructor(
    private val branchAddUseCase: BranchAddUseCase,
    private val getUseCase: BranchGetUseCase,
    private val deleteUseCase: BranchDeleteUseCase
) : ViewModel() {


    private val _result = MutableStateFlow(UseCaseResult())
    val result = _result.asStateFlow()

    private val _itemList = MutableStateFlow<List<Profile>>(listOf())
    val itemList = _itemList.asStateFlow()

    private val _selectedProfileForDelete = MutableStateFlow<Profile?>(null)
    val selectedProfileForDelete = _selectedProfileForDelete.asStateFlow()

    private val _selectedProfileForEdit = MutableStateFlow<Profile?>(null)
    val selectedProfileForEdit = _selectedProfileForEdit.asStateFlow()

    fun clearResultData() {
        _result.value = UseCaseResult()
    }

    fun saveBranchData(branchDetails: Profile) {
        viewModelScope.launch(Dispatchers.IO) {
            branchAddUseCase.invoke(branchDetails).collect {
                resetSelectedForEdit()
                resetSelectedForDelete()
                val result = UseCaseResult()
                result.resultCode = 200
                result.isLoading = false
                _result.emit(result)

            }
            // branchAddNetworkUseCase.invoke(branchDetails) // TODO Need to host server
        }
    }

    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            _itemList.value = getUseCase.invoke()
        }
    }

    fun deleteRecord(customer: Profile) {
        viewModelScope.launch(Dispatchers.Default) {
            deleteUseCase.invoke(customer)
            resetSelectedForEdit()
            resetSelectedForDelete()
            getAll()
        }
    }

    fun selectedForDelete(item: Profile) {
        _selectedProfileForDelete.value = item
    }

    fun selectedForEdit(item: Profile) {
        _selectedProfileForEdit.value = item
    }

    fun resetSelectedForEdit() {
        _selectedProfileForEdit.value = null
    }

    fun resetSelectedForDelete() {
        _selectedProfileForDelete.value = null
    }

}