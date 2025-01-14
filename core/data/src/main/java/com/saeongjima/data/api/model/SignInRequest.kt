package com.saeongjima.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)
