package com.example.pdfgenerator.domain

import com.example.pdfgenerator.data.model.Customer
import com.example.pdfgenerator.data.model.ItemsInventory
import com.example.pdfgenerator.data.model.ItemsMasterEntry
import com.example.pdfgenerator.data.model.Profile
import com.example.pdfgenerator.extension.convertToDouble


data class InventoryDomainData(
    val item: ItemsMasterEntry?,
    val numberOfItem: String,
    val unitPrice: String,
    val discount: String,
) {

    var totalGstPerecent: Double = 0.0
    var totalAmountWithoutGst: Double = 0.0
    var totalGstAmount: Double = 0.0
    var totalAmountWithGstPrice: String = ""
}

fun List<InventoryDomainData>.getInventoryDbData(): List<ItemsInventory> {
    return this.map { it.converToItemInventory() }
}

fun InventoryDomainData.converToItemInventory(): ItemsInventory {
    val item = ItemsInventory(this.item?.itemId ?: 0, numberOfItem, unitPrice, discount)
    item.totalGstAmount = this.totalGstAmount.toString()
    item.totalAmountWithGst = this.totalAmountWithGstPrice
    item.totalAmountWithOutGst = this.totalAmountWithoutGst.toString()
    return item
}

fun List<InventoryDomainData>.getTotalAmount(): Double {
    var total = 0.0
    this.forEach { it ->
        total = total + it.totalAmountWithGstPrice.convertToDouble(0.0)
    }
    return total
}

data class PrinterDataItem(val branch: Profile, val customer: Customer) {
    var date: String = ""
    var invoiceId: String = ""
    var amountInWords: String = ""
    var total: String = ""
    var itemList: List<InventoryDomainData> = listOf()
}