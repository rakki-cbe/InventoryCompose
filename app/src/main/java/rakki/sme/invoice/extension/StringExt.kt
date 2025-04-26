package rakki.sme.invoice.extension

import androidx.compose.runtime.MutableState

fun String?.filterNull(default: String = ""): String {
    return this ?: default
}

fun String.convertToInt(default: Int): Int {
    try {
        return this.toInt()
    } catch (e: NumberFormatException) {
        return default
    }
}

fun String?.convertToDouble(default: Double): Double {
    this?.let {
        try {
            return this.toDouble()
        } catch (e: NumberFormatException) {
            return default
        }
    }
    return default
}

fun MutableState<String>.clear() {
    this.value = ""
}

fun String.isValidAmountDigitOrEmpty(): Boolean {
    if (this == "") return true
    else {
        return this.toDoubleOrNull() != null

    }
}