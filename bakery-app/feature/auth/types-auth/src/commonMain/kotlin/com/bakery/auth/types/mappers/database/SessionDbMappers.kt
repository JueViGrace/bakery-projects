package com.bakery.auth.types.mappers.database

import com.bakery.types.auth.Session
import migrations.Session as DBSession

fun DBSession.toSession(): Session = Session(
    id = id,
    accessToken = access_token,
    refreshToken = refresh_token,
    active = active,
)
