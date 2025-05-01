package rakki.sme.invoice.data

enum class NavigationGraphBiller {
    Home,
    CustomerList,
    AddCustomer,
    AddNewBranch,
    BranchList,
    EditBranch,
    AddNewItem,
    ItemList,
    EditItem,
    StartInvoice,
    EditCustomer,
    Back,
}

val RESUTLT_USECASE_SUCCESS = 200
val DefaultSelectedItem: Long = -1