package com.example.pdfgenerator.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly
import com.example.pdfgenerator.R
import com.example.pdfgenerator.extension.isValidAmountDigitOrEmpty

@Composable
fun PlainInputText(
    label: Int, itemValue: MutableState<String>,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        TextField(value = itemValue.value, label = {
            Text(
                text = stringResource(label),
                modifier = modifier.fillMaxWidth()
            )
        }, onValueChange = { it ->
            itemValue.value = it
        }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun NumbersInputText(
    label: Int, itemValue: MutableState<String>,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        TextField(
            value = itemValue.value, label = {
            Text(
                text = stringResource(label),
                modifier = modifier.fillMaxWidth()
            )
        }, onValueChange = { it ->
            if (it.isDigitsOnly())
                itemValue.value = it
        }, keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.NumberPassword
        ), modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun AmountInputText(
    label: Int, itemValue: MutableState<String>,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        TextField(
            value = itemValue.value, label = {
            Text(
                text = stringResource(label),
                modifier = modifier.fillMaxWidth()
            )
        }, onValueChange = { it ->
            if (it.isValidAmountDigitOrEmpty())
                itemValue.value = it
        }, keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ), modifier = Modifier.fillMaxWidth()
        )
    }
}

