package com.bakery.core.util

import com.toxicbakery.bcrypt.Bcrypt

object Kbcrypt {
    private const val SALT_ROUNDS = 12

    fun verifyPassword(
        password: String,
        hashedPassword: String,
    ): Boolean = Bcrypt.verify(password, hashedPassword.toByteArray())

    fun hashPassword(password: String): String = Bcrypt.hash(input = password, saltRounds = SALT_ROUNDS).decodeToString()
}
