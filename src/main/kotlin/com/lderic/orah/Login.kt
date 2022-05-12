package com.lderic.orah


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Login(
    @SerialName("password")
    val password: String,
    @SerialName("username")
    val username: String
)