package com.example.pdfgenerator.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.pdfgenerator.R
import com.example.pdfgenerator.data.NavigationGraphBiller
import com.example.pdfgenerator.ui.theme.PdfGeneratorTheme
import com.example.pdfgenerator.viewmodel.BillerEntryViewModel

@Composable
fun homeScreen(
    modifier: Modifier = Modifier,
    billerEntryViewModel: BillerEntryViewModel,
    navigate: (route: String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_large)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_large))
    ) {
        fullSizeCardButton(stringResource(R.string.button_add_invoice), {
            navigate.invoke(NavigationGraphBiller.StartInvoice.name)
        })
        fullSizeCardButton(stringResource(R.string.button_add_new_customer), {
            navigate.invoke(NavigationGraphBiller.AddCustomer.name)
        })
        fullSizeCardButton(stringResource(R.string.button_add_new_item), {
            navigate.invoke(NavigationGraphBiller.AddNewItem.name)
        })
        fullSizeCardButton(stringResource(R.string.button_add_new_branch), {
            navigate.invoke(NavigationGraphBiller.AddNewBranch.name)
        })
    }

}

@Composable
fun fullSizeCardButton(text: String, navigation: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.card_height_small))
            .padding(dimensionResource(R.dimen.padding_medium))
            .clickable {
                navigation.invoke()
            }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center, text = text
            )
        }
    }
}

@Preview
@Composable
fun preview_fullSizeCardButton() {
    PdfGeneratorTheme {
        fullSizeCardButton(stringResource(R.string.button_add_new_customer)) { }
    }
}