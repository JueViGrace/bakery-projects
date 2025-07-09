package com.bakery.ui

import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.invalid_character
import com.bakery.resources.generated.resources.required_field
import com.bakery.types.common.isValidInt
import org.jetbrains.compose.resources.getString

suspend fun String.isValidNumber(): String? = if (this.isEmpty()) {
    getString(Res.string.required_field)
} else if (this.isValidInt() == null) {
    "${getString(Res.string.invalid_character)} (${this.last()})"
} else {
    null
}
