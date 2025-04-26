package rakki.sme.invoice.ui.user

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import rakki.sme.invoice.R
import rakki.sme.invoice.extension.clear
import rakki.sme.invoice.extension.filterNull
import rakki.sme.invoice.ui.component.PlainInputText
import rakki.sme.invoice.viewmodel.UserCredentialsViewModel

@Composable
fun LoginScreen(
    viewModel: UserCredentialsViewModel,
    modifier: Modifier = Modifier,
    navigation: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    ) {
        val userName = rememberSaveable {
            mutableStateOf("")
        }
        val password = rememberSaveable {
            mutableStateOf("")
        }
        val result = viewModel.result.collectAsState()
        val ctx = LocalContext.current
        if (result.value.resultCode == 200) {
            viewModel.storeCredential(result.value.data)
            navigation.invoke()
            viewModel.clearResultData()
        }
        PlainInputText(
            label = R.string.label_user_username,
            itemValue = userName
        )
        PlainInputText(
            label = R.string.label_user_password,
            itemValue = password
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
                    userName.clear()
                    password.clear()
                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(R.string.button_clear))
            }
            Button(
                onClick = {
                    viewModel.loginUser(
                        userName.value.filterNull(""),
                        password.value.filterNull("")
                    )

                }, modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(stringResource(R.string.button_save))
            }
        }

    }
}

