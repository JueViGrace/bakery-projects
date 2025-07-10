package com.bakery.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
* Generic server response data class
* It receives the actual body of the response in the generic data field
* */
@Serializable
data class ApiResponse<T>(
    @SerialName("status")
    val status: Int,
    @SerialName("description")
    val description: String,
    @SerialName("data")
    val data: T? = null,
    @SerialName("message")
    val message: String,
    @SerialName("time")
    val time: String,
)
