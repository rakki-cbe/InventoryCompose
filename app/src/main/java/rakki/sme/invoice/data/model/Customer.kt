package rakki.sme.invoice.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Customer(
    @ColumnInfo(name = "company_name") var companyName: String,
    @ColumnInfo(name = "address") var address: String,
    @ColumnInfo(name = "phone_number") var phoneNumber: String,
    @ColumnInfo(name = "gst") var gst: String
) {
    @PrimaryKey(autoGenerate = true)
    var custId: Long = 0
}

@Entity
data class Profile(
    @ColumnInfo(name = "company_name") val companyName: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "gst") val gst: String,
    @ColumnInfo(name = "bank_name") val bankName: String,
    @ColumnInfo(name = "account_number") val accountNumber: String,
    @ColumnInfo(name = "ifsc") val ifscCode: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "profileId")
    var customerProfileId: Long = 0
}

@Entity
data class ItemsMasterEntry(
    @ColumnInfo(name = "name") val itemName: String,
    @ColumnInfo(name = "code") val itemCode: String
) {
    @ColumnInfo(name = "des")
    var desc: String = ""
    @ColumnInfo(name = "sgst")
    var sgst: String = ""
    @ColumnInfo(name = "cgst")
    var cgst: String = ""
    @ColumnInfo(name = "igst")
    var igst: String = ""

    @ColumnInfo(name = "totalgst")
    var totalGst: String = ""
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "itemId")
    var itemId: Long = 0
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = Customer::class,
        parentColumns = arrayOf("custId"),
        childColumns = arrayOf("customerId"),
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = Profile::class,
            parentColumns = arrayOf("profileId"),
            childColumns = arrayOf("profileId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Inventory(
    @ColumnInfo(name = "customerId") val companyId: Long,
    @ColumnInfo(name = "profileId") val profileID: Long,
    @ColumnInfo(name = "totalAmount") val totalAmountPaid: String,
    @ColumnInfo(name = "discount") val discount: String,
    @ColumnInfo(name = "finalReceivedAmt") val finalAmount: String,
    @ColumnInfo(name = "date") val date: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "billId")
    var billId: Int = 0
}

@Entity(
    foreignKeys = [ForeignKey(
        entity = Inventory::class,
        parentColumns = arrayOf("billId"),
        childColumns = arrayOf("billId"),
        onDelete = ForeignKey.CASCADE
    ),
        ForeignKey(
            entity = ItemsMasterEntry::class,
            parentColumns = arrayOf("itemId"),
            childColumns = arrayOf("itemId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class ItemsInventory(
    @ColumnInfo(name = "itemId") val itemId: Long,
    @ColumnInfo("quantity") val numberOfItem: String,
    @ColumnInfo("unitPrice") val unitPrice: String,
    @ColumnInfo("discount") val discount: String,
) {
    @ColumnInfo(name = "billId")
    var billerId: Long = 0

    @ColumnInfo(name = "totalAmountWithOutGst")
    var totalAmountWithOutGst: String = ""

    @ColumnInfo(name = "totalAmountWithGst")
    var totalAmountWithGst: String = ""

    @ColumnInfo(name = "totalGstAmount")
    var totalGstAmount: String = ""

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "itemInventoryId")
    var id: Long = 0
}

data class InvoiceUserDisplayData(
    @ColumnInfo(name = "customerId") val companyId: Long,
    @ColumnInfo(name = "billId") var billId: Int = 0,
    @ColumnInfo(name = "totalAmount") val totalAmountPaid: String,
    @ColumnInfo(name = "discount") val discount: String,
    @ColumnInfo(name = "finalReceivedAmt") val finalAmount: String,
    @ColumnInfo(name = "date") val date: String
) {
    @ColumnInfo(name = "company_name")
    var companyName: String? = ""
    @ColumnInfo(name = "address")
    var address: String? = ""
    @ColumnInfo(name = "phone_number")
    var phoneNumber: String? = ""
    @ColumnInfo(name = "email")
    var email: String? = ""
    @ColumnInfo(name = "gst")
    var gst: String? = ""
    @ColumnInfo(name = "bank_name")
    var bankName: String? = ""
    @ColumnInfo(name = "account_number")
    var accountNumber: String? = ""
    @ColumnInfo(name = "ifsc")
    var ifscCode: String? = ""
    @ColumnInfo(name = "cust_company_name")
    var custCompanyName: String? = ""
    @ColumnInfo(name = "cust_address")
    var custAddress: String? = ""
    @ColumnInfo(name = "cust_phone_number")
    var custPhoneNumber: String? = ""
    @ColumnInfo(name = "cust_gst")
    var custGst: String? = ""
}

data class InvoiceLineItemForPrint(
    @ColumnInfo(name = "itemId") val itemId: Long,
    @ColumnInfo("quantity") val numberOfItem: String,
    @ColumnInfo("unitPrice") val unitPrice: String,
    @ColumnInfo("discount") val discount: String
) {

    @ColumnInfo(name = "totalAmountWithOutGst")
    var totalAmountWithOutGst: String = ""

    @ColumnInfo(name = "totalAmountWithGst")
    var totalAmountWithGst: String = ""

    @ColumnInfo(name = "totalGstAmount")
    var totalGstAmount: String = ""

    @ColumnInfo(name = "name")
    var itemName: String? = ""

    @ColumnInfo(name = "code")
    var itemCode: String? = ""

    @ColumnInfo(name = "des")
    var desc: String = ""

    @ColumnInfo(name = "sgst")
    var sgst: String = ""

    @ColumnInfo(name = "cgst")
    var cgst: String = ""

    @ColumnInfo(name = "igst")
    var igst: String = ""

    @ColumnInfo(name = "totalgst")
    var totalGst: String = ""


}