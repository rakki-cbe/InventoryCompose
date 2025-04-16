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
import com.example.pdfgenerator.data.customer.ItemsMasterEntry
import com.example.pdfgenerator.extension.clear
import com.example.pdfgenerator.extension.filterNull
import com.example.pdfgenerator.ui.component.AmountInputText
import com.example.pdfgenerator.ui.component.PlainInputText
import com.example.pdfgenerator.viewmodel.ItemMasterListViewModel

@Composable
fun addItemMasterData(
    viewModel: ItemMasterListViewModel,
    modifier: Modifier = Modifier,
    navigation: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ) {
        val name = rememberSaveable {
            mutableStateOf("")
        }
        val code = rememberSaveable {
            mutableStateOf("")
        }
        val des = rememberSaveable {
            mutableStateOf("")
        }
        val sgst = rememberSaveable {
            mutableStateOf("")
        }
        val cgst = rememberSaveable {
            mutableStateOf("")
        }
        val igst = rememberSaveable {
            mutableStateOf("")
        }
        val result = viewModel.result.collectAsState()
        if (result.value.resultCode == 200) {
            navigation.invoke()
            viewModel.clearResultData()
        }
        PlainInputText(
            label = R.string.label_item_name,
            itemValue = name
        )
        PlainInputText(
            label = R.string.label_item_code,
            itemValue = code
        )
        PlainInputText(
            label = R.string.label_item_des,
            itemValue = des
        )
        AmountInputText(
            label = R.string.label_item_sgst,
            itemValue = sgst
        )
        AmountInputText(
            label = R.string.label_item_cgst,
            itemValue = cgst
        )
        AmountInputText(
            label = R.string.label_item_igst,
            itemValue = igst
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
                    name.clear()
                    code.clear()
                    des.clear()
                    sgst.clear()
                    cgst.clear()
                    igst.clear()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(R.string.button_clear))
            }
            Button(
                onClick = {
                    viewModel
                        .saveItemMasterData(
                            ItemsMasterEntry(
                                itemName = name.value.filterNull(),
                                itemCode = code.value.filterNull()
                            ).apply {
                                this.desc = des.value.filterNull()
                                this.sgst = sgst.value.filterNull()
                                this.cgst = cgst.value.filterNull()
                                this.igst = igst.value.filterNull()
                            })
                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(R.string.button_save))
            }
        }

    }
}

