package com.bakery.auth.domain.model.mappers

import com.bakery.types.auth.Session
import migrations.Session as DBSession

fun Session.toDbSession(): DBSession {
    return DBSession(
        id = id,
        access_token = accessToken,
        refresh_token = refreshToken,
        active = active
    )
}
