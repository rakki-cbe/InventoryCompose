package rakki.sme.invoice.extension

fun Char.isValidAmountDigitOrEmpty(): Boolean {
    val data = this.toString()
    if (data == "") return true
    else {
        return data.toDoubleOrNull() != null

    }
}