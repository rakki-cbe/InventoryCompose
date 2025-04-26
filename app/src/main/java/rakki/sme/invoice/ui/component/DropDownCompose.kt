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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import rakki.sme.invoice.R
import rakki.sme.invoice.ui.theme.PdfGeneratorTheme

@Composable
fun DropDown(dropDownUiData: DropDownUiData, resetData: Boolean) {

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }
    val itemPosition = rememberSaveable {
        mutableStateOf(dropDownUiData.itemPosition.value)
    }
    if (resetData)
        itemPosition.value = 0
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
                Text(text = dropDownUiData.listItem[itemPosition.value].second)
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
                dropDownUiData.listItem.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        text = {
                            Text(text = item.second)
                        },
                        onClick = {
                            isDropDownExpanded.value = false
                            /**
                             * if first item or last item selected we don't set that position to selected
                             */
                            if (!((dropDownUiData.ignoreFirst && index == 0)
                                        || (dropDownUiData.ignorelast && dropDownUiData.listItem.size - 1 == index))
                            ) {
                                itemPosition.value = index
                            }
                            dropDownUiData.onDropdownSelected.invoke(dropDownUiData.listItem[index])
                        })
                }
            }
        }

    }
}

data class DropDownUiData(
    val listItem: List<Pair<Long, String>>,
    val onDropdownSelected: (selected: Pair<Long, String>) -> Unit
) {
    var ignoreFirst: Boolean = false
    var ignorelast: Boolean = false
    val itemPosition: MutableStateFlow<Int> = MutableStateFlow(0)
}

@Preview(showBackground = true)
@Composable
fun DropDownDemoPreview() {
    PdfGeneratorTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DropDown(DropDownUiData(listOf(), {}), false)
        }

    }
}