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
import rakki.sme.invoice.ui.UseCaseResult
import javax.inject.Inject

@HiltViewModel
class ItemMasterListViewModel @Inject constructor(
    private val setItemMasterUseCase: ItemMasterAddUseCase
) : ViewModel() {

    private val _result = MutableStateFlow(UseCaseResult())
    val result = _result.asStateFlow()


    fun clearResultData() {
        _result.value = UseCaseResult()
    }

    fun saveItemMasterData(item: ItemsMasterEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            setItemMasterUseCase.invoke(item).collect {
                _result.value = UseCaseResult().apply {
                    resultCode = RESUTLT_USECASE_SUCCESS
                    isLoading = false
                }
            }
        }
    }
}