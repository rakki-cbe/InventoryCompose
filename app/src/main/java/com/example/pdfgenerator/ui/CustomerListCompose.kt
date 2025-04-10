package com.example.pdfgenerator.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.pdfgenerator.InventoryScree
import com.example.pdfgenerator.R
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.viewmodel.BillerEntryViewModel

@Composable
fun loadCustomerList(billerEntryViewModel: BillerEntryViewModel?,
                          modifier: Modifier = Modifier,list: List<Customer>,navigate:(route:String)->Unit){

    Column(modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
        Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = stringResource(R.string.label_customer_list),
                modifier = modifier
            )
            TextButton(onClick = {
                navigate.invoke(InventoryScree.AddCustomer.name)
            }) {
                Text(
                    text = stringResource(R.string.button_add),
                    modifier = modifier
                )
            }
        }
        Column(verticalArrangement = Arrangement.spacedBy(
            dimensionResource(R.dimen.padding_medium)
        ),horizontalAlignment = Alignment.CenterHorizontally) {
            list?.forEach { it ->
                loadCustomerItem(it,modifier,{})
            }

        }
    }

}

@Composable
fun loadCustomerItem(it: Customer, modifier: Modifier, function: () -> Unit) {
    var selected by rememberSaveable {
        mutableStateOf(false)
    }
    Column(modifier =Modifier.fillMaxWidth().padding(dimensionResource(R.dimen.padding_medium)).clickable {
        selected = !selected
    }) {
        Text(
            text = it.companyName,
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Row(
            horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.label_address) + " : " + it.address,
                textAlign = TextAlign.Start
            )
            Text(
                text =  it.phoneNumber,
                textAlign = TextAlign.End
            )
        }

        if(selected) {
            Row(
                horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = it.bankName,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = it.accountNumber,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}
