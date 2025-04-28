package rakki.sme.invoice.ui.component

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
import rakki.sme.invoice.R
import rakki.sme.invoice.extension.convertToDouble
import rakki.sme.invoice.extension.isValidAmountDigitOrEmpty

@Composable
fun PlainInputText(
    label: Int, itemValue: MutableState<String>,
    modifier: Modifier = Modifier, data: InputTextValidation = InputTextValidation()
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
    modifier: Modifier = Modifier,
    data: InputTextValidation = InputTextValidation()
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
                if (checkTextIsValid(it, data, { it.isDigitsOnly() }))
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
    modifier: Modifier = Modifier,
    data: InputTextValidation = InputTextValidation()
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
                if (checkTextIsValid(it, data, { it.isValidAmountDigitOrEmpty() }))
                itemValue.value = it
        }, keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal
        ), modifier = Modifier.fillMaxWidth()
        )
    }
}

fun checkTextIsValid(
    it: String, data: InputTextValidation,
    fieldSpecificValidation: () -> Boolean
): Boolean {
    return if (it.length > data.maxLength && data.maxLength != -1) false
    else if (data.isPercentage && it.convertToDouble(0.0) > 100.00) false
    else fieldSpecificValidation.invoke()
}

data class InputTextValidation(val maxLength: Int = -1) {
    var isPercentage: Boolean = false
}

