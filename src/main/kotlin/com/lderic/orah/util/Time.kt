package com.lderic.orah.util

import kotlinx.coroutines.delay
import java.time.Instant
import java.util.*

object Time {
    suspend fun waitUntilNext() {
        val time = nextPrettyPrint()
        delay(time)
    }

    fun millisecondToString(origin: Long): String {
        //47964995
        val ms = origin % 1000
        var s = (origin / 1000).toInt()
        var min = s / 60
        val hour = min / 60
        min %= 60
        s %= 60
        return "${hour}小时${min}分钟${s}秒${ms}毫秒"
    }

    fun from(hour: Int, minute: Int, date: Int): String {

        val d = Date()
        d.hours = hour
        d.minutes = minute
        d.seconds = 0
        d.date = date
        return d.toInstant().toString()
    }

    fun fromString(fourChars: String, date: Int = Date().date): String {
        var s: String
        try {
            s = from(
                (fourChars[0].toString() + fourChars[1].toString()).toInt(),
                (fourChars[2].toString() + fourChars[3].toString()).toInt(),
                date
            )
        } catch (ignored: Exception) {
            s = Instant.now().toString()
        }
        return s
    }

    fun getNext(): Long {
        val d = Date()

        d.hours = 8
        d.minutes = 30
        d.seconds = 0
        return if (System.currentTimeMillis() >= d.time) {
            d.time + 86400000
        } else d.time
    }

    fun nextPrettyPrint(): Long {
        val time = getNext() - System.currentTimeMillis()
        println("下次做orah的时间是 ${Date(getNext())}, 还剩${millisecondToString(time)}, ${time}毫秒, 现在开始计时")
        return time
    }
}

