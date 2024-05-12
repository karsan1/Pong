
package com.example.project5


import android.content.Context
import android.view.View
import java.util.TimerTask


class GameTimerTask(private val update: () -> Unit) : TimerTask() {
    override fun run() {
        update()
    }
}