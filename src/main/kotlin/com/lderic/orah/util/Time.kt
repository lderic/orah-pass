package com.lderic.orah.util

import kotlinx.coroutines.delay
import java.time.Instant
import java.util.*

@Suppress("DEPRECATION")
object Time {
    private var isNextCancel = false

    suspend fun waitUntilNext() {
        do {
            if (isNextCancel) {
                println("时间被更改! 取消本次Orah")
            }
            isNextCancel = false
            val time = nextPrettyPrint()
            delay(time)
        } while (isNextCancel)
    }

    fun cancelNext() {
        if (isNextCancel) {
            println("下次Orah已经被取消了!")
        } else {
            isNextCancel = true
            println("操作成功, 已取消下次Orah")
        }
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
        return try {
            from(
                (fourChars[0].toString() + fourChars[1].toString()).toInt(),
                (fourChars[2].toString() + fourChars[3].toString()).toInt(),
                date
            )
        } catch (ignored: Exception) {
            Instant.now().toString()
        }
    }

    fun getNext(): Long {
        val d = Date()

        d.hours = 8
        d.minutes = 30
        d.seconds = 0
        if (System.currentTimeMillis() >= d.time) {
            d.time += 86400000
            if (d.day == 6) {
                d.time += 172800000
            }
            d.time
        }
        return d.time
    }

    fun nextPrettyPrint(): Long {
        return (getNext() - System.currentTimeMillis()).also {
            println("下次做orah的时间是 ${Date(getNext())}, 还剩${millisecondToString(it)}, ${it}毫秒, 现在开始计时")
        }
    }
}

