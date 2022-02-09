package com.lderic.orah.response
import kotlinx.serialization.Serializable

@Serializable
data class IsSuccessResponse(
    val success: Boolean
)