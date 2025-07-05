package com.bakery.types.common

import kotlin.math.roundToInt

fun Double.roundWithDecimals(decimals: Int = 2): String {
    val intPart: Int = this.toInt()
    val decimalPart: Int = ((this - intPart) * 100).roundToInt()
    return "$intPart.${decimalPart.toString().padStart(decimals, '0')}"
}

fun Float.roundWithDecimals(decimals: Int = 2): String {
    val intPart: Int = this.toInt()
    val decimalPart: Int = ((this - intPart) * 100).roundToInt()
    return "$intPart.${decimalPart.toString().padStart(decimals, '0')}"
}
