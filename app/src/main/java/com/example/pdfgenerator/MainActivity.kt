package com.example.pdfgenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pdfgenerator.ui.addCustomerDetails
import com.example.pdfgenerator.ui.loadCustomerList
import com.example.pdfgenerator.ui.theme.PdfGeneratorTheme
import com.example.pdfgenerator.viewmodel.BillerEntryViewModel
import com.example.pdfgenerator.viewmodel.BillerUiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val billerEntryViewModel: BillerEntryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PdfGeneratorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    getCustomerInfo(billerEntryViewModel =  billerEntryViewModel, billerUiState = billerEntryViewModel.billerUiState,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }

        billerEntryViewModel.getCustomerData({})

    }
}

enum class InventoryScree {
    CustomerList,
    CustomerDetails,
    AddCustomer
}
@Composable
fun getCustomerInfo(billerEntryViewModel: BillerEntryViewModel?,billerUiState:BillerUiState,modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = InventoryScree.CustomerList.name
    ){
        composable(route = InventoryScree.CustomerList.name){
            billerEntryViewModel?.let {
                val listItem by it.customerListUiState.collectAsState()
                loadCustomerList(billerEntryViewModel,modifier,listItem,{ it->
                    navController.navigate(it)
                })
            }

        }
        composable(route = InventoryScree.AddCustomer.name ) {
            addCustomerDetails(billerEntryViewModel, billerUiState, modifier)
        }
    }


}


@Preview(showBackground = true)
@Composable
fun getCustomerInfoPreview() {
    PdfGeneratorTheme {
        getCustomerInfo(billerEntryViewModel = null, billerUiState = BillerUiState())
    }
}