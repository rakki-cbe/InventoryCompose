package com.example.pdfgenerator.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.pdfgenerator.R
import com.example.pdfgenerator.data.DefaultSelectedItem
import com.example.pdfgenerator.domain.InventoryDomainData
import com.example.pdfgenerator.ui.component.DropDown
import com.example.pdfgenerator.ui.component.DropDownUiData
import com.example.pdfgenerator.ui.component.NumbersInputText
import com.example.pdfgenerator.viewmodel.CreateInvoiceViewModel

@Composable
fun createInvoice(

    viewModel: CreateInvoiceViewModel,
    modifier: Modifier = Modifier,
    navigation: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ) {
        val clearAllUiData = rememberSaveable {
            mutableStateOf<Boolean>(false)
        }
        val selectedProfile = rememberSaveable {
            mutableStateOf<Long>(0)
        }
        val selectedCustomer = rememberSaveable {
            mutableStateOf<Long>(0)
        }
        val selecteditem = rememberSaveable {
            mutableStateOf<Long>(0)
        }
        val resetToDefault = rememberSaveable {
            mutableStateOf<Boolean>(false)
        }

        val tempInventoryItem = viewModel.itemTempInvntoryItem.collectAsState()

        val quantity = rememberSaveable {
            mutableStateOf("")
        }
        val unitPrice = rememberSaveable {
            mutableStateOf("")
        }
        val discount = rememberSaveable {
            mutableStateOf("")
        }
        val result = viewModel.result.collectAsState()
        if (result.value.resultCode == 200) {
            navigation.invoke()
            viewModel.clearResultData()
        }

        //Branch selection
        val branchList = viewModel.branchListUiState.collectAsState()
        val branchListPair: MutableList<Pair<Long, String>> = mutableListOf(
            Pair(
                -1,
                stringResource(R.string.label_select_branch)
            )
        )
        branchList.value.forEach { item ->
            branchListPair.add(Pair(item.customerProfileId, item.companyName))
        }
        dropDownlistComposeInventroy(branchListPair, clearAllUiData, selectedProfile)
        //Customer
        val customerList = viewModel.customerListUiState.collectAsState()
        val customerListPair: MutableList<Pair<Long, String>> = mutableListOf(
            Pair(
                -1,
                stringResource(R.string.label_select_customer)
            )
        )
        customerList.value.forEach { item ->
            customerListPair.add(Pair(item.custId, item.companyName))
        }
        dropDownlistComposeInventroy(customerListPair, clearAllUiData, selectedCustomer)
        //Item
        val itemslist = viewModel.itemMasterListUiState.collectAsState()
        val itemListPair: MutableList<Pair<Long, String>> = mutableListOf(
            Pair(
                -1,
                stringResource(R.string.label_select_item)
            )
        )
        itemslist.value.forEach { item ->
            itemListPair.add(Pair(item.itemId, item.itemName))
        }
        dropDownlistComposeInventroy(itemListPair, clearAllUiData, selecteditem)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.padding_large)
            )
        ) {
            NumbersInputText(
                R.string.label_quantity, quantity, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            NumbersInputText(
                R.string.label_unit_price, unitPrice, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.padding_large)
            )
        ) {
            NumbersInputText(
                R.string.label_unit_discount, discount, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            Button(
                onClick = {
                    viewModel.addTempInventoryItem(
                        InventoryDomainData(
                            item = itemslist.value.filter { it.itemId == selecteditem.value }
                                .firstOrNull(),
                            numberOfItem = quantity.value,
                            unitPrice = unitPrice.value,
                            discount = discount.value
                        )
                    )
                    quantity.value = ""
                    unitPrice.value = ""
                    discount.value = ""

                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(R.string.button_save))
            }
        }
        if (tempInventoryItem.value.isNotEmpty()) {
            loadInventroyInfo(tempInventoryItem.value, {})
        }
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
                    clearAllUiData.value = true
                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(R.string.button_clear))
            }
            Button(
                onClick = {
                    viewModel.saveItemMasterData(
                        tempInventoryItem.value,
                        selectedProfile.value,
                        selectedCustomer.value
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

@Composable
fun loadInventroyInfo(value: List<InventoryDomainData>, function: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .horizontalScroll(rememberScrollState())
    ) {
        Column(modifier = Modifier.width(dimensionResource(R.dimen.card_width_large))) {
            var i = 0
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dimensionResource(R.dimen.card_height_xxs))
                    .background(Color.LightGray),
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {
                VerticalDivider(modifier = Modifier.fillMaxHeight())
                Text(
                    stringResource(R.string.label_sno),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5f)
                )
                VerticalDivider(modifier = Modifier.fillMaxHeight())
                Text(
                    stringResource(R.string.label_item),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f)
                )
                VerticalDivider(modifier = Modifier.fillMaxHeight())
                Text(
                    stringResource(R.string.label_quantity_short),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                VerticalDivider(modifier = Modifier.fillMaxHeight())
                Text(
                    stringResource(R.string.label_unit_short),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                VerticalDivider(modifier = Modifier.fillMaxHeight())
                Text(
                    stringResource(R.string.label_discount_short),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                VerticalDivider(modifier = Modifier.fillMaxHeight())
                Text(
                    stringResource(R.string.label_gst_short),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                VerticalDivider(modifier = Modifier.fillMaxHeight())
                Text(
                    stringResource(R.string.label_total_short),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

            }

            value.forEach {
                i++
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(R.dimen.card_height_xxs)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
                ) {
                    VerticalDivider(modifier = Modifier.fillMaxHeight())
                    Text(
                        i.toString(), modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5f)
                    )
                    VerticalDivider(modifier = Modifier.fillMaxHeight())
                    Text(
                        it.item?.itemName.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1.5f)
                    )
                    VerticalDivider(modifier = Modifier.fillMaxHeight())
                    Text(
                        it.numberOfItem, modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    VerticalDivider(modifier = Modifier.fillMaxHeight())
                    Text(
                        it.unitPrice, modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    VerticalDivider(modifier = Modifier.fillMaxHeight())
                    Text(
                        it.discount,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    VerticalDivider(modifier = Modifier.fillMaxHeight())
                    Text(
                        String.format("%.2f", it.totalGstPerecent) + "%",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    VerticalDivider(modifier = Modifier.fillMaxHeight())
                    Text(
                        it.totalPrice, modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun dropDownlistComposeInventroy(
    listUiState: List<Pair<Long, String>>,
    resetSelection: MutableState<Boolean>,
    selectedId: MutableState<Long>
) {
    Row(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
        val dropDownUi = DropDownUiData(listUiState, { it ->
            when (it.first) {
                DefaultSelectedItem -> {
                    //Do nothing its just label
                }

                else -> {
                    selectedId.value = it.first
                    resetSelection.value = false
                }
            }
        })
        dropDownUi.ignoreFirst = true
        dropDownUi.ignorelast = false
        DropDown(dropDownUi, resetSelection.value)
    }
}

