package rakki.sme.invoice.ui.compose

import android.content.Context
import android.content.Intent
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.FileProvider
import rakki.sme.invoice.R
import rakki.sme.invoice.domain.InventoryDomainData
import rakki.sme.invoice.extension.convertNullOrEmpty
import rakki.sme.invoice.ui.component.AmountInputText
import rakki.sme.invoice.ui.component.DropDown
import rakki.sme.invoice.ui.component.InputTextValidation
import rakki.sme.invoice.ui.component.NumbersInputText
import rakki.sme.invoice.ui.component.showErrorAlert
import rakki.sme.invoice.viewmodel.CreateInvoiceViewModel
import java.io.File

@Composable
fun createInvoice(
    viewModel: CreateInvoiceViewModel,
    modifier: Modifier = Modifier,
    navigation: () -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ) {
        val selectedProfile = viewModel.selectedBranchId.collectAsState()
        val selectedCustomer = viewModel.selectedCustomerId.collectAsState()
        val selectedItem = viewModel.selectedItemId.collectAsState()

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
            result.value.data?.let {
                moveToGeneratePDF(context, it)
                viewModel.clearAllSelectedItem()
            }
            navigation.invoke()
            viewModel.clearResultData()
        }

        val errorMessage = viewModel.errorMessage.collectAsState()
        if (errorMessage.value.isNotEmpty()) {
            showErrorAlert("", errorMessage.value, {
                viewModel.resetErrorMessage()
            })
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
        dropDownlistComposeInventroy(branchListPair, selectedProfile.value, {
            viewModel.setSelectedProfile(it)
        })
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
        dropDownlistComposeInventroy(customerListPair, selectedCustomer.value, {
            viewModel.setSelectedCustomer(it)
        })
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
        dropDownlistComposeInventroy(itemListPair, selectedItem.value, {
            viewModel.setSelectedItem(it)
        })
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.padding_large)
            )
        ) {
            val qtyValidation = InputTextValidation(maxLength = 5)
            NumbersInputText(
                R.string.label_quantity, quantity, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), data = qtyValidation
            )
            val unitValidation = InputTextValidation(maxLength = 10)
            AmountInputText(
                R.string.label_unit_price, unitPrice, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                data = unitValidation
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
                    .weight(1f),
                data = InputTextValidation(maxLength = 6)
            )
            Button(
                onClick = {
                    viewModel.addTempInventoryItem(
                        context,
                            InventoryDomainData(
                                item = itemslist.value.filter { it.itemId == selectedItem.value }
                                    .firstOrNull(),
                                numberOfItem = quantity.value.convertNullOrEmpty("0"),
                                unitPrice = unitPrice.value.convertNullOrEmpty("0"),
                                discount = discount.value.convertNullOrEmpty("0")
                            )
                        )
                        quantity.value = ""
                        unitPrice.value = ""
                        discount.value = ""

                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(R.string.button_add))
            }
        }
        if (tempInventoryItem.value.isNotEmpty()) {
            loadInventroyInfo(tempInventoryItem.value, { viewModel.deleteTempItem(it) })
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
                    viewModel.clearAllSelectedItem()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(R.string.button_clear))
            }
            Button(
                onClick = {
                    viewModel.saveItemMasterData(
                        inventoryDomain = tempInventoryItem.value,
                        profileId = selectedProfile.value,
                        customerId = selectedCustomer.value,
                        context = context
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
fun loadInventroyInfo(value: List<InventoryDomainData>, delete: (InventoryDomainData) -> Unit) {
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
                VerticalDivider(modifier = Modifier.fillMaxHeight())
                Text(
                    stringResource(R.string.label_edit),
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
                    verticalAlignment = Alignment.CenterVertically,
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
                        it.totalAmountWithGstPrice, modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )
                    VerticalDivider(modifier = Modifier.fillMaxHeight())
                    IconButton(onClick = {
                        delete.invoke(it)
                    }) {
                        Icon(
                            painterResource(R.drawable.ic_delete),
                            stringResource(R.string.label_delete)
                        )
                    }
                }
                HorizontalDivider(modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun dropDownlistComposeInventroy(
    listUiState: List<Pair<Long, String>>,
    selectedId: Long,
    onItemSelected: (Long) -> Unit
) {
    Row(modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))) {
        DropDown(listUiState, { onItemSelected(it.first) }, selectedId)
    }
}

fun moveToGeneratePDF(context: Context, filePath: String) {
    val path = FileProvider.getUriForFile(
        context,
        "rakki.sme.invoice.provider",
        File(filePath)
    )
    val pdfIntent = Intent(Intent.ACTION_VIEW)
    pdfIntent.setDataAndType(path, "application/pdf")
    pdfIntent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
    pdfIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.startActivity(pdfIntent)
}

