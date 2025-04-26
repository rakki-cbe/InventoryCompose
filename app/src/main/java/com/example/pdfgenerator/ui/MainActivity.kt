package com.example.pdfgenerator.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pdfgenerator.R
import com.example.pdfgenerator.data.NavigationGraphBiller
import com.example.pdfgenerator.ui.compose.CustomToolbarScreen
import com.example.pdfgenerator.ui.compose.addBranchDetailsDialog
import com.example.pdfgenerator.ui.compose.addCustomerDetails
import com.example.pdfgenerator.ui.compose.addItemMasterData
import com.example.pdfgenerator.ui.compose.createInvoice
import com.example.pdfgenerator.ui.compose.homeScreen
import com.example.pdfgenerator.ui.theme.PdfGeneratorTheme
import com.example.pdfgenerator.viewmodel.BillerEntryViewModel
import com.example.pdfgenerator.viewmodel.BranchViewModel
import com.example.pdfgenerator.viewmodel.CreateInvoiceViewModel
import com.example.pdfgenerator.viewmodel.CustomerViewModel
import com.example.pdfgenerator.viewmodel.ItemMasterListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val billerEntryViewModel: BillerEntryViewModel by viewModels()
    val itemMasterListViewModel: ItemMasterListViewModel by viewModels()
    val branchViewModel: BranchViewModel by viewModels()
    val customerViewModel: CustomerViewModel by viewModels()
    val createInvoiceViewModel: CreateInvoiceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createInvoiceViewModel.getAllBranch()
        createInvoiceViewModel.getCustomer()
        createInvoiceViewModel.getItem()
        enableEdgeToEdge()
        setContent {
            PdfGeneratorTheme {
                val state = rememberScrollState()
                val navController = rememberNavController()
                val title = rememberSaveable {
                    mutableStateOf("")
                }
                LaunchedEffect(Unit) { state.animateScrollTo(100) }
                Scaffold(topBar = {
                    CustomToolbarScreen(navController, title.value)
                },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = NavigationGraphBiller.Home.name
                    ) {
                        composable(route = NavigationGraphBiller.Home.name) {
                            title.value = stringResource(R.string.title_home)
                            homeScreen(
                                billerEntryViewModel = billerEntryViewModel
                            ) { it ->
                                navController.navigate(it)
                            }
                        }
                        composable(route = NavigationGraphBiller.AddNewBranch.name) {
                            title.value = stringResource(R.string.title_add_branch)
                            addBranchDetailsDialog(
                                viewModel = branchViewModel,
                                onAdded = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(route = NavigationGraphBiller.AddNewItem.name) {
                            title.value = stringResource(R.string.title_add_item)
                            addItemMasterData(
                                viewModel = itemMasterListViewModel,
                                navigation = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(route = NavigationGraphBiller.AddCustomer.name) {
                            title.value = stringResource(R.string.title_add_cutomer)
                            addCustomerDetails(
                                customerViewModel = customerViewModel,
                                navigation = {
                                    navController.popBackStack()
                                })
                        }
                        composable(route = NavigationGraphBiller.StartInvoice.name) {
                            title.value = stringResource(R.string.title_invoice)
                            createInvoice(
                                viewModel = createInvoiceViewModel,
                                navigation = {
                                    navController.popBackStack()
                                })
                        }

                        /*composable(route = NavigationGraphBiller.CustomerList.name) {
                            title.value = stringResource(R.string.title_addCustomer)
                            val listItem by customerViewModel.customerListUiState.collectAsState()
                            loadCustomerList(
                                customerViewModel = customerViewModel,
                                list = listItem,
                                navigate = { it ->
                                    navController.navigate(it)
                                })

                        }*/
                    }
                }
            }
        }
    }
}

class UseCaseResult {
    var isLoading: Boolean = false
    var resultCode: Int = 0
    var errorCode: Int = 0
}

class Response<T> {
    var isLoading: Boolean = false
    var resultCode: Int = 0
    var errorCode: Int = 0
    var data: T? = null
}
