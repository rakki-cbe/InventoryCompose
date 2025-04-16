package com.example.pdfgenerator.data.customer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Customer(@ColumnInfo(name = "company_name") val companyName:String,
                        @ColumnInfo(name = "address") val address:String,
                        @ColumnInfo(name = "phone_number") val phoneNumber:String,
                    @ColumnInfo(name = "gst") val gst: String
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
    @ColumnInfo(name = "customerId") val companyName: Long,
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

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "itemInventoryId")
    var id: Long = 0
}