package com.example.pdfgenerator.extension

fun Char.isValidAmountDigitOrEmpty(): Boolean {
    val data = this.toString()
    if (data == "") return true
    else {
        return if (data.toDoubleOrNull() == null) false
        else true

    }
}