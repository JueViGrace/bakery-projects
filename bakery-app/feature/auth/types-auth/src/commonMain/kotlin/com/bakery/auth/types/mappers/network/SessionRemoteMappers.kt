package com.bakery.auth.types.mappers.network

import com.bakery.auth.network.model.response.AuthResponse
import com.bakery.types.auth.Session
import migrations.Session as DBSession

fun AuthResponse.toSession(): Session = Session(
    id = id,
    accessToken = accessToken,
    refreshToken = refreshToken,
    active = false,
)

fun AuthResponse.toDbSession(active: Boolean): DBSession = DBSession(
    id = id,
    access_token = accessToken,
    refresh_token = refreshToken,
    active = active,
)
