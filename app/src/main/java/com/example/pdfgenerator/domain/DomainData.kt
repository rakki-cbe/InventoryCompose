package com.example.pdfgenerator.domain

import com.example.pdfgenerator.data.model.ItemsInventory
import com.example.pdfgenerator.data.model.ItemsMasterEntry
import com.example.pdfgenerator.extension.convertToDouble


data class InventoryDomainData(
    val item: ItemsMasterEntry?,
    val numberOfItem: String,
    val unitPrice: String,
    val discount: String,
) {

    var totalGstPerecent: Double = 0.0
    var totalPrice: String = ""
}

fun List<InventoryDomainData>.getInventoryDbData(): List<ItemsInventory> {
    return this.map { it.converToItemInventory() }
}

fun InventoryDomainData.converToItemInventory(): ItemsInventory {
    return ItemsInventory(this.item?.itemId ?: 0, numberOfItem, unitPrice, discount)
}

fun List<InventoryDomainData>.getTotalAmount(): Double {
    var total = 0.0
    this.forEach { it ->
        total = total + it.totalPrice.convertToDouble(0.0)
    }
    return total
}