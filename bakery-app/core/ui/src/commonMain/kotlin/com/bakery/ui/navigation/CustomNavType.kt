package com.bakery.ui.navigation

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import kotlinx.serialization.json.Json

object CustomNavType {
    inline fun <reified T> createNavType(): NavType<T> {
        return object : NavType<T>(isNullableAllowed = false) {
            override fun put(bundle: SavedState, key: String, value: T) {
                bundle.write {
                    putString(key, Json.encodeToString(value))
                }
            }

            override fun get(bundle: SavedState, key: String): T? {
                return Json.decodeFromString(bundle.read { getStringOrNull(key) } ?: return null)
            }

            override fun parseValue(value: String): T = Json.decodeFromString(value)

            override fun serializeAsValue(value: T): String = Json.encodeToString(value)
        }
    }
}
