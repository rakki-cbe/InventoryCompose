package rakki.sme.invoice.ui.compose

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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import rakki.sme.invoice.R
import rakki.sme.invoice.data.NavigationGraphBiller
import rakki.sme.invoice.data.model.ItemsMasterEntry
import rakki.sme.invoice.extension.clear
import rakki.sme.invoice.extension.convertNullOrEmpty
import rakki.sme.invoice.extension.filterNull
import rakki.sme.invoice.ui.component.AmountInputText
import rakki.sme.invoice.ui.component.InputTextValidation
import rakki.sme.invoice.ui.component.PlainInputText
import rakki.sme.invoice.viewmodel.ItemMasterListViewModel

@Composable
fun addItemMasterData(
    viewModel: ItemMasterListViewModel,
    modifier: Modifier = Modifier,
    navigation: (String) -> Unit,
    item: ItemsMasterEntry? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ) {
        val name = rememberSaveable {
            mutableStateOf(item?.itemName ?: "")
        }
        val code = rememberSaveable {
            mutableStateOf(item?.itemCode ?: "")
        }
        val des = rememberSaveable {
            mutableStateOf(item?.desc ?: "")
        }
        val sgst = rememberSaveable {
            mutableStateOf(item?.sgst ?: "")
        }
        val cgst = rememberSaveable {
            mutableStateOf(item?.cgst ?: "")
        }
        val igst = rememberSaveable {
            mutableStateOf(item?.igst ?: "")
        }
        val result = viewModel.result.collectAsState()
        if (result.value.resultCode == 200) {
            navigation.invoke(NavigationGraphBiller.Back.name)
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
        val validation = InputTextValidation(maxLength = 5).apply {
            isPercentage = true
        }
        AmountInputText(
            label = R.string.label_item_sgst,
            itemValue = sgst,
            data = validation
        )
        AmountInputText(
            label = R.string.label_item_cgst,
            itemValue = cgst,
            data = validation
        )
        AmountInputText(
            label = R.string.label_item_igst,
            itemValue = igst,
            data = validation
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
                                this.sgst = sgst.value.convertNullOrEmpty("0.0")
                                this.cgst = cgst.value.convertNullOrEmpty("0.0")
                                this.igst = igst.value.convertNullOrEmpty("0.0")
                                if (item != null)
                                    itemId = item.itemId
                            })

                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(R.string.button_save))
            }
        }
        TextButton(
            onClick = { navigation.invoke(NavigationGraphBiller.ItemList.name) },
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            )
        ) {
            Text(text = stringResource(R.string.label_edit_existing_item))
        }

    }
}

