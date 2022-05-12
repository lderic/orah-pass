package com.lderic.orah

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.cookies.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess
import kotlinx.serialization.json.Json as json

const val LOGIN_URL = "https://api-virginia.orah.com/1/login"
const val NEW_SCHEDULE_URL = "https://orah-api-virginia.orah.com/1/student-api/schedule-new-leave?"
const val CANCEL_URL = "https://api-virginia.orah.com/1/leave/entry/student/upcomingCancel"
const val USERNAME = "iliduo@hotmail.com"
const val PASSWORD = "eric52coco"

val cookies = mutableListOf<Cookie>()

val client = HttpClient() {
    install(HttpCookies) {
        storage = object : CookiesStorage {
            override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
                cookies.add(cookie)
            }

            override fun close() {
                // Nothing here
            }

            override suspend fun get(requestUrl: Url): List<Cookie> {
                return cookies
            }
        }
    }

    install(JsonFeature) {
        serializer = KotlinxSerializer(json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        })
    }

    BrowserUserAgent()
}

fun main(): Unit = runBlocking {
    login()
    newSignIn(Time(date = 13, hour = 9, minute = 0), Time(date = 13, hour = 15, minute = 0)).also { println(it) }
}


suspend fun login() {
    client.post<HttpStatement>("https://api-virginia.orah.com/1/login") {
        contentType(ContentType.Application.Json)
        header("referer", "https://app.orah.com/")
        body = Login(PASSWORD, USERNAME)
    }.execute {
        if (it.status == HttpStatusCode.OK) {
            println("Login success")
        } else {
            println("Login failed")
            exitProcess(0)
        }
    }
}

suspend fun newSignIn(start: Time, end: Time): Int {
    val response = client.post<OrahResponse>("https://orah-api-virginia.orah.com/1/student-api/schedule-new-leave?") {
        contentType(ContentType.Application.Json)
        header("referer", "https://app.orah.com/")
        body = createNewOrahLeave(start, end)
    }
    if (response.success) println("Success") else println("Failed")
    return response.leave.id
}

suspend fun cancel(id: Int) {
    client.delete<HttpStatement>(CANCEL_URL) {
        contentType(ContentType.Application.Json)
        header("referer", "https://app.orah.com/")
        body = cancelOrah(id)
    }.execute {
        if (it.status.isSuccess()) println("Cancel success") else println("Cancel failed")
    }
}