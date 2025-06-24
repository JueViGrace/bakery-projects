package com.bakery.api.users.service

import com.bakery.api.users.repository.UserRepository
import com.bakery.models.response.AppResponse
import com.bakery.models.response.DefaultHttpResponse
import com.bakery.models.user.UserDto
import com.bakery.models.user.toDto
import com.bakery.models.user.toUpdate
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class UserService(
    private val userRepository: UserRepository,
    private val coroutineContext: CoroutineContext
) {
    suspend fun getAllUsers(): AppResponse<List<UserDto>> {
        return try {
            withContext(coroutineContext) {
                val users = userRepository.findAll().map { it.toDto() }

                if (users.isNotEmpty()) {
                    DefaultHttpResponse.ok(users)
                } else {
                    DefaultHttpResponse.notFound("Users not found")
                }
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun getOneUser(id: Int): AppResponse<UserDto?> {
        return try {
            withContext(coroutineContext) {
                val user = findUser(id)

                if (user != null) {
                    DefaultHttpResponse.ok(user)
                } else {
                    DefaultHttpResponse.notFound("User with id $id does not exists")
                }
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun getOneUserByEmail(email: String): AppResponse<UserDto> {
        return try {
            withContext(coroutineContext) {
                val user = userRepository.findOneByEmail(email)?.toDto()

                if (user != null) {
                    DefaultHttpResponse.ok(user)
                } else {
                    DefaultHttpResponse.notFound("User with email $email was not found")
                }
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun updateUser(userDto: UserDto): AppResponse<UserDto?> {
        return try {
            withContext(coroutineContext) {
                findUser(userDto.userId)
                    ?: return@withContext DefaultHttpResponse.notFound("User with id ${userDto.userId} does not exists")

                val savedUser = userRepository.update(id = userDto.userId, e = userDto.toUpdate())?.toDto()

                if (savedUser != null) {
                    DefaultHttpResponse.ok(savedUser)
                } else {
                    DefaultHttpResponse.badRequest("Failed to update user")
                }
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    suspend fun deleteUser(id: Int): AppResponse<String> {
        return try {
            withContext(coroutineContext) {
                findUser(id)
                    ?: return@withContext DefaultHttpResponse.notFound("User with id $id does not exists")

                val deleted = userRepository.softDelete(id)

                if (deleted != null) {
                    DefaultHttpResponse.ok("User with id $id was deleted successfully")
                } else {
                    DefaultHttpResponse.badRequest("Unable to delete user")
                }
            }
        } catch (e: Exception) {
            error(e)
        }
    }

    private suspend fun findUser(id: Int): UserDto? {
        return withContext(coroutineContext) {
            userRepository.findOneById(id)?.toDto()
        }
    }
}
