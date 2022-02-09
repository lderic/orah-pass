package com.lderic.orah.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeaveRequest(
    @SerialName("leaveTypeId")
    val leaveTypeId: Int,
    @SerialName("leaveInfo")
    val leaveInfo: LeaveInfo,
    @SerialName("requestInfo")
    val requestInfo: RequestInfo,
    @SerialName("action")
    val action: String,
) {
    @Serializable
    data class LeaveInfo(
        @SerialName("departure_time")
        val departureTime: String,
        @SerialName("return_time")
        val returnTime: String,
        @SerialName("location")
        val location: Int,
        @SerialName("other_location_name")
        val otherLocationName: String = "",
        @SerialName("note")
        val note: String,
        @SerialName("leave_type")
        val leaveType: LeaveType,
    ) {
        @Serializable
        data class LeaveType(
            @SerialName("auto_sign_out")
            val autoSignOut: Boolean,
            @SerialName("name")
            val name: String,
            @SerialName("color")
            val color: String,
            @SerialName("color_hex")
            val colorHex: String,
            @SerialName("original")
            val original: Int,
        )
    }

    @Serializable
    data class RequestInfo(
        @SerialName("adHocApprovers")
        val adHocApprovers: List<String> = emptyList(),
    )
}