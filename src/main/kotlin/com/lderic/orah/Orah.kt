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
    client.post<HttpStatement>(LOGIN_URL) {
        contentType(ContentType.Application.Json)
        header("referer", "https://app.orah.com/")
        body = "{\"username\": \"$USERNAME\", \"password\": \"$PASSWORD\"}"
    }.execute  {
        if (it.status == HttpStatusCode.OK) {
            println("Login success")
        } else {
            println("Login failed")
            exitProcess(0)
        }
    }
    client.post<OrahResponse>(NEW_SCHEDULE_URL) {
        contentType(ContentType.Application.Json)
        header("referer", "https://app.orah.com/")
        body = createNewOrahLeave(Time(9, 0), Time(15, 0))
    }.also { if (it.success) println("Success") else println("Failed") }
}
