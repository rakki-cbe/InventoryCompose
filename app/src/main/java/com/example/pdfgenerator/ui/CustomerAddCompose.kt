package com.example.pdfgenerator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.MutableLiveData
import com.example.pdfgenerator.R
import com.example.pdfgenerator.viewmodel.BillerEntryViewModel
import com.example.pdfgenerator.viewmodel.BillerUiState

@Composable
fun addCustomerDetails(billerEntryViewModel: BillerEntryViewModel?,
                          billerUiState: BillerUiState,
                          modifier: Modifier = Modifier){
    Column {
        Text(
            text = "Please enter your customer information",
            modifier = modifier
        )
        var clearStateUi:Boolean by rememberSaveable  {
            mutableStateOf(false)
        }
        customerEntryRow(label = R.string.label_company_name,billerUiState.companyNameUi,clearStateUi)
        customerEntryRow(label = R.string.label_address,billerUiState.addressStateUi,clearStateUi)
        customerEntryRow(label = R.string.label_phone_number,billerUiState.phoneNumberStateUi,clearStateUi)
        customerEntryRow(label = R.string.label_bank_name,billerUiState.bankNameStateUi,clearStateUi)
        customerEntryRow(label = R.string.label_account_number,billerUiState.accountNumberStateUi,clearStateUi)
        customerEntryRow(label = R.string.label_ifsc_code,billerUiState.ifscStateUi,clearStateUi)
        customerEntryRow(label = R.string.label_gst,billerUiState.gstStateUi,clearStateUi)
        Row {
            Button(onClick = {
                clearStateUi = true
            }) {
                Text(stringResource(R.string.button_clear))
            }
            Button(onClick = {
                billerEntryViewModel?.saveCustomerData{
                    clearStateUi = true
                }
            }) {
                Text(stringResource(R.string.button_save))
            }
        }

    }
}

@Composable
fun customerEntryRow(label: Int, itemValue: MutableLiveData<String>, clear:Boolean,
                     modifier: Modifier = Modifier) {
    Row(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
        var itemValueData: String by rememberSaveable  {
            mutableStateOf(itemValue.value ?: "" )
        }
        if(clear) itemValueData = ""
        Text(
            text = stringResource(label),
            modifier = modifier.padding(end = dimensionResource(R.dimen.padding_medium))
        )
        TextField(value = itemValueData, onValueChange = { it ->
            itemValue.value = it
            itemValueData =it
        })
    }
}