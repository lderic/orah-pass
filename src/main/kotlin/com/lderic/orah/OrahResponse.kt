package com.lderic.orah


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrahResponse(
    @SerialName("leave")
    val leave: Leave,
    @SerialName("success")
    val success: Boolean
) {
    @Serializable
    data class Leave(
        @SerialName("id")
        val id: Int
    )
}