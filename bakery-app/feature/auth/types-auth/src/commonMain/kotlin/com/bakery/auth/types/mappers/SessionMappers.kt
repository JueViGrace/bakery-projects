package com.bakery.auth.types.mappers

import com.bakery.types.auth.Session
import migrations.Session as DBSession

fun Session.toDbSession(): DBSession =
    DBSession(
        id = id,
        access_token = accessToken,
        refresh_token = refreshToken,
        active = active,
    )
