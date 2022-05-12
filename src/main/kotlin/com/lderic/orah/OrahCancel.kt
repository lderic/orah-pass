package com.lderic.orah


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class OrahCancel(
    @SerialName("id")
    val id: Int,
    @SerialName("requestInfo")
    val requestInfo: RequestInfo
) {
    @Serializable
    data class RequestInfo(
        @SerialName("comment")
        val comment: String,
        @SerialName("lastUpdatedAt")
        val lastUpdatedAt: String
    )
}

fun cancelOrah(id: Int, comment: String = ""): OrahCancel {
    return OrahCancel(id, OrahCancel.RequestInfo(comment, Instant.now().toString()))
}