package rakki.sme.invoice.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import rakki.sme.invoice.data.model.Customer
import rakki.sme.invoice.extension.filterNull
import rakki.sme.invoice.ui.component.PlainInputText
import rakki.sme.invoice.viewmodel.CustomerViewModel

@Composable
fun addCustomerDetails(
    customerViewModel: CustomerViewModel,
    modifier: Modifier = Modifier,
    navigation: (String) -> Unit,
    customer: Customer? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        val companyNameUi = rememberSaveable {
            mutableStateOf(customer?.companyName ?: "")
        }
        val addressStateUi = rememberSaveable {
            mutableStateOf(customer?.address ?: "")
        }
        val phoneNumberStateUi = rememberSaveable {
            mutableStateOf(customer?.phoneNumber ?: "")
        }
        val gstStateUi = rememberSaveable {
            mutableStateOf(customer?.gst ?: "")
        }
        val result = customerViewModel.result.collectAsState()
        if (result.value.resultCode == 200) {
            navigation.invoke(NavigationGraphBiller.Back.name)
            customerViewModel.clearResultData()
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
                    if (customer == null) {
                        companyNameUi.value = ""
                        addressStateUi.value = ""
                        phoneNumberStateUi.value = ""
                        gstStateUi.value = ""
                    } else {
                        navigation.invoke(NavigationGraphBiller.Back.name)
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text =
                        stringResource(
                            if (customer == null) R.string.button_clear
                            else R.string.button_cancel
                        )
                )
            }
            Button(onClick = {
                customerViewModel
                    .saveCustomerData(
                        Customer(
                            companyName = companyNameUi.value.filterNull(),
                            address = addressStateUi.value.filterNull(),
                            phoneNumber = phoneNumberStateUi.value.filterNull(),
                            gst = gstStateUi.value.filterNull()
                        ).apply {
                            if (customer != null)
                                custId = customer.custId
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
            onClick = { navigation.invoke(NavigationGraphBiller.CustomerList.name) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = stringResource(R.string.label_edit_existing_customer))
        }
    }
}