package com.lderic.orah

import com.lderic.orah.thread.AutoThread
import com.lderic.orah.thread.CommandThread
import com.lderic.orah.util.Users

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Users.load()
        val autoThread = AutoThread
        val commandThread = CommandThread
        autoThread.start()
        commandThread.start()
    }
}