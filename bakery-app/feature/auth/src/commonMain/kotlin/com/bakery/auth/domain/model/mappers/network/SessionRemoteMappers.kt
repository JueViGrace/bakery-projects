package com.bakery.auth.domain.model.mappers.network

import com.bakery.auth.network.model.response.AuthResponse
import com.bakery.types.auth.Session

fun AuthResponse.toSession(): Session {
    return Session(
        id = id,
        accessToken = accessToken,
        refreshToken = refreshToken,
        active = false
    )
}
