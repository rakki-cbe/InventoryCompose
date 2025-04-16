package com.example.pdfgenerator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.pdfgenerator.data.customer.Profile
import com.example.pdfgenerator.extension.filterNull
import com.example.pdfgenerator.ui.component.PlainInputText
import com.example.pdfgenerator.viewmodel.BranchViewModel

@Composable
fun addBranchDetailsDialog(
    viewModel: BranchViewModel,
    modifier: Modifier = Modifier,
    onAdded: () -> Unit
) {
    val companyName = rememberSaveable {
        mutableStateOf("")
    }
    val address = rememberSaveable {
        mutableStateOf("")
    }
    val email = rememberSaveable {
        mutableStateOf("")
    }
    val phone = rememberSaveable {
        mutableStateOf("")
    }
    val gst = rememberSaveable {
        mutableStateOf("")
    }
    val bank = rememberSaveable {
        mutableStateOf("")
    }
    val accountNumber = rememberSaveable {
        mutableStateOf("")
    }

    val ifsc = rememberSaveable {
        mutableStateOf("")
    }
    val result = viewModel.result.collectAsState()
    if (result.value.resultCode == 200) {
        onAdded.invoke()
        viewModel.clearResultData()
    }

    Column(
        modifier = Modifier
            .padding(
                start = dimensionResource(R.dimen.padding_large),
                end = dimensionResource(R.dimen.padding_large)
            )
            .verticalScroll(rememberScrollState())
    ) {
        PlainInputText(
            label = R.string.label_company_name,
            itemValue = companyName
        )
        PlainInputText(
            label = R.string.label_address,
            address
        )
        PlainInputText(
            label = R.string.label_phone_number,
            phone,
        )
        PlainInputText(
            label = R.string.label_email,
            email
        )
        PlainInputText(
            label = R.string.label_gst,
            gst
        )
        PlainInputText(
            label = R.string.label_bank_name,
            bank
        )
        PlainInputText(
            label = R.string.label_account_number,
            accountNumber
        )
        PlainInputText(
            label = R.string.label_ifsc_code,
            ifsc
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
                    companyName.value = ""
                    address.value = ""
                    phone.value = ""
                    email.value = ""
                    bank.value = ""
                    accountNumber.value = ""
                    ifsc.value = ""
                    gst.value = ""
                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(R.string.button_clear))
            }
            Button(
                onClick = {
                    viewModel.saveBranchData(
                        Profile(
                            companyName = companyName.value.filterNull(),
                            address = address.value.filterNull(),
                            phoneNumber = phone.value.filterNull(),
                            email = email.value.filterNull(),
                            gst = gst.value.filterNull(),
                            bankName = bank.value.filterNull(),
                            accountNumber = accountNumber.value.filterNull(),
                            ifscCode = ifsc.value.filterNull()
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
