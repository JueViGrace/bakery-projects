package com.bakery.types.auth

/**
* Session type
* */
data class Session(val id: String, val accessToken: String, val refreshToken: String, val active: Boolean) {
    companion object {
        fun mapToSession(id: String, accessToken: String, refreshToken: String, active: Boolean): Session = Session(
            id = id,
            accessToken = accessToken,
            refreshToken = refreshToken,
            active = active,
        )
    }
}
