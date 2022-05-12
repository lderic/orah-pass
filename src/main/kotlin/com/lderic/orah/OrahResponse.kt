package com.lderic.orah


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrahResponse(
    @SerialName("success")
    val success: Boolean
)