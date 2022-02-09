package com.lderic.orah.thread

import com.lderic.orah.Orah
import com.lderic.orah.util.Time
import com.lderic.orah.util.Users
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

object CommandThread : Thread() {
    override fun run() {
        while (true) {
            when (readln()) {
                "stop" -> {
                    exitProcess(0)
                }
                "try" -> {
                    runBlocking {
                        Users.forEach {
                            Orah.checkAvailable(it.username, it.password)
                        }
                    }
                }
                "reload" -> {
                    Users.load()
                }
                "orah" -> {
                    runBlocking {
                        Users.forEach {
                            Orah.doOrah(it.username, it.password)
                        }
                    }
                }
                "all" -> {
                    Users.forEach {
                        print("${it.username} -> ")
                        for (i in it.password.indices - 1) {
                            print("*")
                        }
                        println("*")
                    }
                }
                "time" -> {
                    Time.nextPrettyPrint()
                }
            }
        }
    }
}