package rakki.sme.invoice.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import rakki.sme.invoice.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showErrorAlert(title: String, errorMessage: String, onDismissRequest: () -> Unit) {
    BasicAlertDialog(
        onDismissRequest = {
            onDismissRequest()
        }) {
        Column(
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_large))
                .background(color = Color.White),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(errorMessage)
            Button(onClick = { onDismissRequest.invoke() }) { Text(text = stringResource(R.string.button_ok)) }
        }

    }

}