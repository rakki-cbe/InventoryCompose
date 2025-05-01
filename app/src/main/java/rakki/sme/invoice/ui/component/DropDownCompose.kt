package rakki.sme.invoice.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import rakki.sme.invoice.R
import rakki.sme.invoice.ui.theme.PdfGeneratorTheme

@Composable
fun DropDown(
    listItem: List<Pair<Long, String>>,
    onDropdownSelected: (selected: Pair<Long, String>) -> Unit, selectedId: Long
) {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                val selectedItem = listItem.firstOrNull { it.first == selectedId }
                if (selectedItem != null) {
                    Text(text = selectedItem.second)
                } else {
                    Text(text = listItem[0].second)
                }

                Image(
                    painter = painterResource(id = R.drawable.drop_down_ic),
                    contentDescription = "DropDown Icon"
                )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                listItem.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = {
                            Text(text = item.second)
                        },
                        onClick = {
                            isDropDownExpanded.value = false
                            /**
                             * if first item or last item selected we don't set that position to selected
                             */
                            onDropdownSelected.invoke(listItem[index])
                        })
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DropDownDemoPreview() {
    PdfGeneratorTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DropDown(listOf(), {}, 0)
        }

    }
}