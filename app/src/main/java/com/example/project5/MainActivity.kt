package com.example.project5

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.Timer

class MainActivity : AppCompatActivity() {
    private lateinit var gameView: GameView
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameView = GameView(this)
        setContentView(gameView)
        startGame()
    }

    private fun startGame() {
        timer = Timer()
        timer?.scheduleAtFixedRate(GameTimerTask { gameView.update() }, 0, 16L)
    }


    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
        gameView.game.highScore = 0
    }
}