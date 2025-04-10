package com.example.pdfgenerator.data.customer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Customer(@ColumnInfo(name = "company_name") val companyName:String,
                        @ColumnInfo(name = "address") val address:String,
                        @ColumnInfo(name = "phone_number") val phoneNumber:String,
                        @ColumnInfo(name = "gst") val gst:String,
                        @ColumnInfo(name = "bank_name") val bankName:String,
                        @ColumnInfo(name = "account_number") val accountNumber:String,
                        @ColumnInfo(name = "ifsc") val ifscCode:String){
    @PrimaryKey(autoGenerate = true)
    var custId: Int = 0

}