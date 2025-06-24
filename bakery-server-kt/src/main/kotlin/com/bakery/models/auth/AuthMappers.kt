package com.bakery.models.auth

import com.bakery.Bakery_token as BakeryToken

fun BakeryToken.toToken(): Token = Token(
    token = token,
    email = user_email
)

fun Token.toDatabase(): BakeryToken = BakeryToken(
    token = token,
    user_email = email
)
