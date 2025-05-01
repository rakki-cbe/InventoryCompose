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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import rakki.sme.invoice.R
import rakki.sme.invoice.data.NavigationGraphBiller
import rakki.sme.invoice.ui.component.CustomToolbarScreen
import rakki.sme.invoice.ui.compose.ListExitingCustomer
import rakki.sme.invoice.ui.compose.ListExitingItems
import rakki.sme.invoice.ui.compose.ListExitingProfile
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
        enableEdgeToEdge()
        setContent {
            PdfGeneratorTheme {
                val state = rememberScrollState()
                val navController = rememberNavController()
                val title = rememberSaveable {
                    mutableStateOf("")
                }
                LaunchedEffect(Unit) { state.animateScrollTo(100) }
                Scaffold(
                    topBar = {
                        CustomToolbarScreen(navController, title.value)
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = NavigationGraphBiller.Home.name
                    ) {
                        composable(route = NavigationGraphBiller.Home.name) {
                            title.value = stringResource(R.string.title_home)
                            homeScreen(
                                billerEntryViewModel = billerEntryViewModel
                            ) {
                                navController.navigate(it)
                            }
                        }
                        composable(route = NavigationGraphBiller.AddNewBranch.name) {
                            title.value = stringResource(R.string.title_add_branch)
                            addBranchDetailsDialog(
                                viewModel = branchViewModel,
                                navigation = {
                                    navigateToScreen(it, navController)
                                }
                            )
                        }
                        composable(route = NavigationGraphBiller.EditBranch.name) {
                            title.value = stringResource(R.string.title_edit_branch)
                            val selectedCustomer =
                                branchViewModel.selectedProfileForEdit.collectAsState()
                            addBranchDetailsDialog(
                                viewModel = branchViewModel,
                                navigation = {
                                    navigateToScreen(it, navController)
                                }, item = selectedCustomer.value
                            )
                        }
                        composable(route = NavigationGraphBiller.BranchList.name) {
                            title.value = stringResource(R.string.title_list_branch)
                            ListExitingProfile(
                                viewModel = branchViewModel,
                                navigation = {
                                    navigateToScreen(it, navController)
                                })
                        }
                        composable(route = NavigationGraphBiller.AddNewItem.name) {
                            title.value = stringResource(R.string.title_add_item)
                            addItemMasterData(
                                viewModel = itemMasterListViewModel,
                                navigation = {
                                    navigateToScreen(it, navController)
                                }
                            )
                        }
                        composable(route = NavigationGraphBiller.EditItem.name) {
                            title.value = stringResource(R.string.title_edit_item)
                            val selectedCustomer =
                                itemMasterListViewModel.selectedItemForEdit.collectAsState()
                            addItemMasterData(
                                viewModel = itemMasterListViewModel,
                                navigation = {
                                    if (it == NavigationGraphBiller.Back.name)
                                        navController.popBackStack()
                                    else navController.navigate(it)
                                }, item = selectedCustomer.value
                            )
                        }
                        composable(route = NavigationGraphBiller.ItemList.name) {
                            title.value = stringResource(R.string.title_list_items)
                            ListExitingItems(
                                viewModel = itemMasterListViewModel,
                                navigation = {
                                    if (it == NavigationGraphBiller.Back.name)
                                        navController.popBackStack()
                                    else
                                        navController.navigate(it)
                                })
                        }
                        composable(route = NavigationGraphBiller.AddCustomer.name) {
                            title.value = stringResource(R.string.title_add_cutomer)
                            addCustomerDetails(
                                customerViewModel = customerViewModel,
                                navigation = {
                                    if (it == NavigationGraphBiller.Back.name)
                                        navController.popBackStack()
                                    else navController.navigate(it)
                                })
                        }
                        composable(route = NavigationGraphBiller.EditCustomer.name) {
                            title.value = stringResource(R.string.title_edit_cutomer)
                            val selectedCustomer =
                                customerViewModel.selectedCustomerForEdit.collectAsState()
                            addCustomerDetails(
                                customerViewModel = customerViewModel,
                                navigation = {
                                    if (it == NavigationGraphBiller.Back.name)
                                        navController.popBackStack()
                                    else navController.navigate(it)
                                }, customer = selectedCustomer.value
                            )
                        }
                        composable(route = NavigationGraphBiller.CustomerList.name) {
                            title.value = stringResource(R.string.title_list_customer)
                            ListExitingCustomer(
                                customerViewModel = customerViewModel,
                                navigation = {
                                    navController.navigate(it)
                                })

                        }
                        composable(route = NavigationGraphBiller.StartInvoice.name) {
                            title.value = stringResource(R.string.title_invoice)
                            createInvoiceViewModel.clearAllSelectedItem()
                            createInvoiceViewModel.getAllBranch()
                            createInvoiceViewModel.getCustomer()
                            createInvoiceViewModel.getItem()
                            createInvoice(
                                viewModel = createInvoiceViewModel,
                                navigation = {
                                    navController.popBackStack()
                                })
                        }
                    }
                }
            }
        }
    }

    private fun navigateToScreen(it: String, navController: NavHostController) {
        if (it == NavigationGraphBiller.Back.name) navController.popBackStack()
        else navController.navigate(it)
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
