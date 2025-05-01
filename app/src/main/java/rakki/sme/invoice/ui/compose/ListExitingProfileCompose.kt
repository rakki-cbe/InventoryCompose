package rakki.sme.invoice.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import rakki.sme.invoice.R
import rakki.sme.invoice.data.NavigationGraphBiller
import rakki.sme.invoice.ui.component.ConfirmDialog
import rakki.sme.invoice.viewmodel.BranchViewModel

@Composable
fun ListExitingProfile(
    modifier: Modifier = Modifier,
    viewModel: BranchViewModel,
    navigation: (String) -> Unit
) {
    viewModel.getAll()
    val customerData = viewModel.itemList.collectAsState()
    /**
     * Check list is empty if empty show no data otherwise show list
     */
    if (customerData.value.isEmpty()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = {
                navigation.invoke(NavigationGraphBiller.Back.name)
            }) {
                Text(text = stringResource(R.string.label_no_data_available))
            }
        }
    } else {
        val showAlertConfirmDialog = remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium)),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.padding_medium)
            )
        ) {
            customerData.value.forEach { item ->
                Card(
                    elevation = CardDefaults.elevatedCardElevation()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_medium))
                    ) {
                        Column(
                            modifier = Modifier.weight(8f),
                            verticalArrangement = Arrangement.spacedBy(
                                dimensionResource(R.dimen.padding_medium)
                            )
                        ) {
                            Text(
                                maxLines = 2,
                                text = item.companyName,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                maxLines = 2,
                                text = item.address,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Row(modifier = Modifier.weight(2f)) {
                            IconButton(onClick = {
                                viewModel.selectedForEdit(item)
                                navigation.invoke(NavigationGraphBiller.EditBranch.name)
                            }) {
                                Icon(
                                    painterResource(R.drawable.ic_edit),
                                    stringResource(R.string.label_edit)
                                )
                            }
                            IconButton(onClick = {
                                viewModel.selectedForDelete(item)
                                showAlertConfirmDialog.value = true
                            }) {
                                Icon(
                                    painterResource(R.drawable.ic_delete),
                                    stringResource(R.string.label_delete)
                                )
                            }
                        }
                    }
                }

            }
        }
        val selectedCustomerForDelet = viewModel.selectedProfileForDelete.collectAsState()
        selectedCustomerForDelet.value?.let {
            if (showAlertConfirmDialog.value) {
                ConfirmDialog(
                    onDismissRequest = {
                        showAlertConfirmDialog.value = false
                    },
                    onConfirmation = {
                        viewModel.deleteRecord(it)
                        viewModel.resetSelectedForDelete()
                    },
                    dialogTitle = stringResource(R.string.title_confirm),
                    dialogText = stringResource(R.string.label_confirm_delete)
                )
            }
        }
    }
}
