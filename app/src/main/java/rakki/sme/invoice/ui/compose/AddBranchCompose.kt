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
import rakki.sme.invoice.data.model.Profile
import rakki.sme.invoice.extension.filterNull
import rakki.sme.invoice.ui.component.PlainInputText
import rakki.sme.invoice.viewmodel.BranchViewModel

@Composable
fun addBranchDetailsDialog(
    viewModel: BranchViewModel,
    modifier: Modifier = Modifier,
    navigation: (String) -> Unit,
    item: Profile? = null
) {
    val companyName = rememberSaveable {
        mutableStateOf(item?.companyName ?: "")
    }
    val address = rememberSaveable {
        mutableStateOf(item?.address ?: "")
    }
    val email = rememberSaveable {
        mutableStateOf(item?.email ?: "")
    }
    val phone = rememberSaveable {
        mutableStateOf(item?.phoneNumber ?: "")
    }
    val gst = rememberSaveable {
        mutableStateOf(item?.gst ?: "")
    }
    val bank = rememberSaveable {
        mutableStateOf(item?.bankName ?: "")
    }
    val accountNumber = rememberSaveable {
        mutableStateOf(item?.accountNumber ?: "")
    }

    val ifsc = rememberSaveable {
        mutableStateOf(item?.ifscCode ?: "")
    }
    val result = viewModel.result.collectAsState()
    if (result.value.resultCode == 200) {
        navigation.invoke(NavigationGraphBiller.Back.name)
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
                        ).apply {
                            if (item != null) {
                                customerProfileId = item.customerProfileId
                            }
                        }
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(R.string.button_save))
            }
        }
        TextButton(
            onClick = { navigation.invoke(NavigationGraphBiller.BranchList.name) },
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            )
        ) {
            Text(text = stringResource(R.string.label_edit_existing_branch))
        }

    }
}
