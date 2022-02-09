package com.lderic.orah.thread

import com.lderic.orah.Orah
import com.lderic.orah.util.Time
import com.lderic.orah.util.Users
import kotlinx.coroutines.runBlocking

object AutoThread : Thread() {
    override fun run() {
        while (true) {
            try {
                runBlocking {
                    Time.waitUntilNext()
                    Users.forEach {
                        Orah.doOrah(it.username, it.password)
                    }
                }
            } catch (_: Exception) {
                println("An Unknown Error Occur!!!")
            }
        }
    }
}