package com.lderic.orah


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class OrahLeave(
    @SerialName("action")
    val action: String = "Create Upcoming Leave",
    @SerialName("leaveInfo")
    val leaveInfo: LeaveInfo = LeaveInfo(),
    @SerialName("leaveTypeId")
    val leaveTypeId: Int = 9419,
    @SerialName("requestInfo")
    val requestInfo: RequestInfo = RequestInfo()
) {
    @Serializable
    data class LeaveInfo(
        @SerialName("departure_time")
        val departureTime: String = "",
        @SerialName("leave_type")
        val leaveType: LeaveType = LeaveType(),
        @SerialName("location")
        val location: Int = 7689,
        @SerialName("note")
        val note: String = "Please note if you have an after school activity here.",
        @SerialName("other_location_name")
        val otherLocationName: String = "",
        @SerialName("return_time")
        val returnTime: String = ""
    ) {
        @Serializable
        data class LeaveType(
            @SerialName("auto_sign_out")
            val autoSignOut: Boolean = true,
            @SerialName("color")
            val color: String = "Yellow",
            @SerialName("color_hex")
            val colorHex: String = "#140381",
            @SerialName("name")
            val name: String = "Class and After School Activity Pass",
            @SerialName("original")
            val original: Int = 9419
        )
    }

    @Serializable
    data class RequestInfo(
        @SerialName("adHocApprovers")
        val adHocApprovers: List<String> = listOf()
    )
}

data class Time(val hour: Int, val minute: Int)

fun createNewOrahLeave(departureTime: Time, returnTime: Time): OrahLeave {
    fun newTime(hour: Int, minute: Int): String {
        val c = Calendar.getInstance()
        if (c.get(Calendar.HOUR_OF_DAY) >= hour) {
            c.add(Calendar.DAY_OF_MONTH, 1)
        }
        c.set(Calendar.HOUR_OF_DAY, hour)
        c.set(Calendar.MINUTE, minute)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        return c.toInstant().toString()
    }
    return OrahLeave(
        leaveInfo = OrahLeave.LeaveInfo(
            departureTime = newTime(departureTime.hour, departureTime.minute),
            returnTime = newTime(returnTime.hour, returnTime.minute)
        )
    )
}