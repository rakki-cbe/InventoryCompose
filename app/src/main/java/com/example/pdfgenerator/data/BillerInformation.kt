package com.example.pdfgenerator.data

data class BillerInformation(val custId:String,val customerDetails:CustomerDetails?= null,val date:String,
                             val invoiceNumber:String,val item:List<Particulars>)

data class Particulars(val no:Int, val description:String, val quantity:Double,
                       val price:Double,val discount:Double = 0.0)

data class CustomerDetails(val custId: String,val companyName:String,val address:String,
                           val phoneNumber:String,val gst:String, val bankName:String,
                           val accountNumber:String,val ifscCode:String)