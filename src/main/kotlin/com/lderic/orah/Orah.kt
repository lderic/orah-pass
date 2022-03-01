package com.lderic.orah

import com.lderic.orah.request.LeaveRequest
import com.lderic.orah.request.LoginRequest
import com.lderic.orah.response.IsSuccessResponse
import com.lderic.orah.util.Ids
import com.lderic.orah.util.Time
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.http.*


object Orah {
    const val newLeaveUrl = "https://orah-api-virginia.orah.com/1/student-api/schedule-new-leave"
    const val loginUrl = "https://api-virginia.orah.com/1/login"

    @Suppress( "unused")
    const val logoutUrl = "https://app.orah.com/logout"
    @Suppress( "unused")
    const val checkUrl = "https://app.orah.com/api/credential/byUsername?email="

    var leaveTime = "0900"
    var returnTime = "1500"

    val cookies = mutableListOf<Cookie>()

    private val httpClient = HttpClient() {
        install(HttpCookies) {
            storage = object : CookiesStorage {

                override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
                    cookies.add(cookie)
                }

                override fun close() {
                }

                override suspend fun get(requestUrl: Url): List<Cookie> {
                    return cookies
                }

            }
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
        BrowserUserAgent()
    }

    suspend fun doOrah(username: String, password: String) {
        try {
            if (!checkAvailable(username, password, false)) {
                logout()
                return
            }
            httpClient.post<IsSuccessResponse>(newLeaveUrl) {
                contentType(ContentType.Application.Json)
                body = LeaveRequest(
                    9419, LeaveRequest.LeaveInfo(
                        departureTime = Time.fromString(leaveTime),
                        returnTime = Time.fromString(returnTime),
                        Ids.JUSTIN_SIENA,
                        note = "Please note if you have an after school activity here.",
                        leaveType = LeaveRequest.LeaveInfo.LeaveType(
                            true, "Class and After School Activity Pass", "Yellow", "#140381", 9419
                        )
                    ), action = "Create Upcoming Leave", requestInfo = LeaveRequest.RequestInfo()
                )
            }
            println("Do orah successful with username $username")
        } catch (e: Exception) {
            println("Can't do orah with username $username")
        }
        logout()
    }

    suspend fun checkAvailable(username: String, password: String, autoLogOut: Boolean = true): Boolean {
        val response = login(username, password)
        if (autoLogOut) {
            logout()
        }
        return if (response) {
            println("Login successful with user $username")
            true
        } else {
            println("Can't login with user $username")
            false
        }
    }

    private suspend fun login(email: String, password: String): Boolean {
        val response = httpClient.post<IsSuccessResponse>(loginUrl) {
            contentType(ContentType.Application.Json)
            body = LoginRequest(email, password)
        }
        return response.success
    }

    private fun logout() {
        cookies.clear()
    }
}
