package com.example.pdfgenerator.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.pdfgenerator.R
import com.example.pdfgenerator.data.NavigationGraphBiller
import com.example.pdfgenerator.data.model.Customer
import com.example.pdfgenerator.viewmodel.CustomerViewModel

@Composable
fun loadCustomerList(
    customerViewModel: CustomerViewModel,
    modifier: Modifier = Modifier, list: List<Customer>, navigate: (route: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                start = dimensionResource(R.dimen.padding_large),
                end = dimensionResource(R.dimen.padding_large)
            )
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.label_customer_list),
                style = MaterialTheme.typography.titleLarge,
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1.5f)
            )
            TextButton(onClick = {
                navigate.invoke(NavigationGraphBiller.AddCustomer.name)
            }, modifier = Modifier.weight(0.5f)) {
                Text(
                    text = stringResource(R.string.button_add),
                    modifier = modifier
                )
            }
        }
        //val listLastItem = list.last() // To avoid loading line at bottom
        list.forEach { it ->
            loadCustomerItem(it, modifier, {})
            HorizontalDivider(
                modifier =
                    Modifier.padding(
                        top = dimensionResource(R.dimen.padding_small),
                        bottom = dimensionResource(R.dimen.padding_small)
                    ), thickness = dimensionResource(R.dimen.divider_thick_light)
            )

        }
    }
}

@Composable
fun loadCustomerItem(it: Customer, modifier: Modifier, function: () -> Unit) {
    var selected by rememberSaveable {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                selected = !selected
            }) {
        Text(
            style = MaterialTheme.typography.bodyLarge,
            text = it.companyName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_medium))
        )
        Text(
            style = MaterialTheme.typography.bodyMedium,
            text = stringResource(R.string.label_address) + " : " + it.address + " /n " +
                    "Ph : " + it.phoneNumber,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_medium))
        )

        if (selected) {
            /*Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                modifier = Modifier.fillMaxWidth().padding(bottom = dimensionResource(R.dimen.padding_medium))
            ) {
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth().weight(1.5f),
                    text = it.bankName,
                    textAlign = TextAlign.Start
                )
                Text(
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth().weight(0.5f),
                    text = it.ifscCode,
                    textAlign = TextAlign.End
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
                modifier = Modifier.fillMaxWidth().padding(bottom = dimensionResource(R.dimen.padding_medium))
            ){
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    text = it.accountNumber,
                    textAlign = TextAlign.Start
                )
                Text(
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    text = it.gst,
                    textAlign = TextAlign.End
                )
            }*/
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
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(stringResource(R.string.button_hide))
                }
                Button(
                    onClick = {

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(stringResource(R.string.button_add_invoice))
                }
            }

        }
    }
}
