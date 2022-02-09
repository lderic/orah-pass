package com.lderic.orah.util

import com.lderic.orah.Orah
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileReader

object Users : Iterable<Users.User> {
    private val userList = mutableMapOf<String, User>()

    data class User(val username: String, val password: String) {}

    private fun register(username: String, password: String) {
        userList[username] = User(username, password)
    }

    private fun clear() {
        userList.clear()
    }

    val size get() = userList.size

    fun del(username: String) {
        userList.remove(username)
    }

    override fun iterator(): Iterator<User> {
        return userList.values.iterator()
    }

    fun load() {
        readUsers()
        runBlocking {
            Users.forEach {
                Orah.checkAvailable(it.username, it.password)
            }
        }
        println("Now there is/are $size user/users")
    }

    private fun readUsers() {
        clear()
        val config = File(System.getProperty("user.dir") + "/config.txt")
        if (!config.exists()) {
            config.createNewFile()
            config.writeText("**LEAVE=0900\n**RETURN=1500//Use \"=\" to split username and password\n//every user use one line\n//example:\n//your_username=your_password\n//if you want write something which is not a user, please add // at first of the new line")
        }
        val reader = FileReader(config)
        val list = reader.readLines()
        var times = 0
        list.forEachIndexed { i, it ->
            if (it.startsWith("**")) {
                try {
                    val s = it.split("=")
                    if (s[0] == "**LEAVE") {
                        Orah.leaveTime = s[0]
                    } else if (s[1] == "**RETURN") {
                        Orah.returnTime = s[0]
                    } else {
                        throw IllegalArgumentException("Start with ** but not a config text")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }else if (it.isNotEmpty() && !it.startsWith("//")) {
                try {
                    it.split("=").let { line ->
                        register(line[0], line[1])
                        println("User ${line[0]} has created")
                    }
                } catch (e: Exception) {
                    println("Exception in config line ${i + 1}, content: \"${it}\", reason: $e")
                }
            } else times++
        }
        if (list.size == times) {
            println("It is empty in file ${config.absolutePath}")
        }
    }
}