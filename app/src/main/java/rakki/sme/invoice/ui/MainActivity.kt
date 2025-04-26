package rakki.sme.invoice.ui

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
import dagger.hilt.android.AndroidEntryPoint
import rakki.sme.invoice.R
import rakki.sme.invoice.data.NavigationGraphBiller
import rakki.sme.invoice.ui.compose.CustomToolbarScreen
import rakki.sme.invoice.ui.compose.addBranchDetailsDialog
import rakki.sme.invoice.ui.compose.addCustomerDetails
import rakki.sme.invoice.ui.compose.addItemMasterData
import rakki.sme.invoice.ui.compose.createInvoice
import rakki.sme.invoice.ui.compose.homeScreen
import rakki.sme.invoice.ui.theme.PdfGeneratorTheme
import rakki.sme.invoice.viewmodel.BillerEntryViewModel
import rakki.sme.invoice.viewmodel.BranchViewModel
import rakki.sme.invoice.viewmodel.CreateInvoiceViewModel
import rakki.sme.invoice.viewmodel.CustomerViewModel
import rakki.sme.invoice.viewmodel.ItemMasterListViewModel

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
