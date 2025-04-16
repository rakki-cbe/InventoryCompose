package com.example.pdfgenerator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.pdfgenerator.R
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.extension.filterNull
import com.example.pdfgenerator.ui.component.PlainInputText
import com.example.pdfgenerator.viewmodel.CustomerViewModel

@Composable
fun addCustomerDetails(
    customerViewModel: CustomerViewModel,
    modifier: Modifier = Modifier, navigation: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        val companyNameUi = rememberSaveable {
            mutableStateOf("")
        }
        val addressStateUi = rememberSaveable {
            mutableStateOf("")
        }
        val phoneNumberStateUi = rememberSaveable {
            mutableStateOf("")
        }
        val gstStateUi = rememberSaveable {
            mutableStateOf("")
        }
        val result = customerViewModel.result.collectAsState()
        if (result.value.resultCode == 200) {
            navigation.invoke()
        }
        PlainInputText(
            label = R.string.label_company_name,
            itemValue = companyNameUi
        )
        PlainInputText(
            label = R.string.label_address,
            itemValue = addressStateUi
        )
        PlainInputText(
            label = R.string.label_phone_number,
            itemValue = phoneNumberStateUi
        )
        PlainInputText(
            label = R.string.label_gst,
            itemValue = gstStateUi
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_large)),
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.padding_large)
            )
        ) {
            FilledTonalButton(
                onClick = {
                    companyNameUi.value = ""
                    addressStateUi.value = ""
                    phoneNumberStateUi.value = ""
                    gstStateUi.value = ""
                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(R.string.button_clear))
            }
            Button(onClick = {
                customerViewModel
                    .saveCustomerData(
                        Customer(
                            companyName = companyNameUi.value.filterNull(),
                            address = addressStateUi.value.filterNull(),
                            phoneNumber = phoneNumberStateUi.value.filterNull(),
                            gst = gstStateUi.value.filterNull()
                        )
                    )
            }, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
            ) {
                Text(stringResource(R.string.button_save))
            }
        }

    }
}