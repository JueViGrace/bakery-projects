package com.bakery.models.user

import com.bakery.common.toDate
import com.bakery.common.toInstant
import com.bakery.models.auth.RegisterDto
import io.ktor.server.http.toHttpDateString
import io.ktor.server.util.toLocalDateTime
import io.ktor.util.InternalAPI
import com.bakery.Bakery_user as BakeryUser

fun BakeryUser.toDto(): UserDto = UserDto(
    userId = id,
    name = name,
    lastname = lastname,
    email = email,
    birthDate = birth_date.toHttpDateString(),
    phone = phone,
    createdAt = created_at?.toHttpDateString(),
    updatedAt = updated_at?.toHttpDateString(),
    deletedAt = deleted_at?.toHttpDateString(),
)

@OptIn(InternalAPI::class)
fun RegisterDto.toInsert(): BakeryUser = BakeryUser(
    id = 0,
    name = name,
    lastname = lastname,
    email = email,
    birth_date = birthDate.toInstant().toDate().toLocalDateTime(),
    phone = phone,
    password = password,
    role = "",
    created_at = null,
    updated_at = null,
    deleted_at = null,
)

@OptIn(InternalAPI::class)
fun UserDto.toUpdate(): BakeryUser = BakeryUser(
    id = userId,
    name = name,
    lastname = lastname,
    birth_date = birthDate.toInstant().toDate().toLocalDateTime(),
    phone = phone,
    role = "",
    email = "",
    password = "",
    created_at = null,
    updated_at = null,
    deleted_at = null,
)
