package rakki.sme.invoice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import rakki.sme.invoice.data.RESUTLT_USECASE_SUCCESS
import rakki.sme.invoice.data.model.ItemsMasterEntry
import rakki.sme.invoice.data.usecase.ItemMasterAddUseCase
import rakki.sme.invoice.data.usecase.ItemMasterDeleteUseCase
import rakki.sme.invoice.data.usecase.ItemMasterGetUseCase
import rakki.sme.invoice.ui.UseCaseResult
import javax.inject.Inject

@HiltViewModel
class ItemMasterListViewModel @Inject constructor(
    private val getUseCase: ItemMasterGetUseCase,
    private val addUseCase: ItemMasterAddUseCase,
    private val deleteUseCase: ItemMasterDeleteUseCase
) : ViewModel() {

    private val _result = MutableStateFlow(UseCaseResult())
    val result = _result.asStateFlow()


    private val _itemList = MutableStateFlow<List<ItemsMasterEntry>>(listOf())
    val itemList = _itemList.asStateFlow()

    private val _selectedItemForDelete = MutableStateFlow<ItemsMasterEntry?>(null)
    val selectedItemForDelete = _selectedItemForDelete.asStateFlow()

    private val _selectedItemForEdit = MutableStateFlow<ItemsMasterEntry?>(null)
    val selectedItemForEdit = _selectedItemForEdit.asStateFlow()

    fun clearResultData() {
        _result.value = UseCaseResult()
    }

    fun saveItemMasterData(item: ItemsMasterEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            addUseCase.invoke(item).collect {
                resetSelectedForDelete()
                resetSelectedForEdit()
                _result.value = UseCaseResult().apply {
                    resultCode = RESUTLT_USECASE_SUCCESS
                    isLoading = false
                }
            }
        }
    }

    fun getAll() {
        viewModelScope.launch(Dispatchers.IO) {
            _itemList.value = getUseCase.invoke()
        }
    }

    fun deleteRecord(customer: ItemsMasterEntry) {
        viewModelScope.launch(Dispatchers.Default) {
            deleteUseCase.invoke(customer)
            resetSelectedForDelete()
            resetSelectedForEdit()
            getAll()
        }
    }

    fun selectedForDelete(item: ItemsMasterEntry) {
        _selectedItemForDelete.value = item
    }

    fun selectedForEdit(item: ItemsMasterEntry) {
        _selectedItemForEdit.value = item
    }

    fun resetSelectedForEdit() {
        _selectedItemForEdit.value = null
    }

    fun resetSelectedForDelete() {
        _selectedItemForDelete.value = null
    }

}