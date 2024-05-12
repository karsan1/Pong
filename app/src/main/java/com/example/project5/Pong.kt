package com.example.project5

import android.media.SoundPool
import android.content.Context
import android.content.SharedPreferences

class Pong(private val screenWidth: Int, private val screenHeight: Int, private val cntxt: Context) {
    // Ball properties
    var builder : SoundPool.Builder = SoundPool.Builder()
    var pool : SoundPool = builder.build()
    var ballX = screenWidth / 2f
    private var pref : SharedPreferences = cntxt.getSharedPreferences( cntxt.packageName + "_preferences",
        Context.MODE_PRIVATE)

    // Set initial ballY to be at the top center
    var ballRadius = 20f
    var ballY = ballRadius + 1
    var ballDX = 0f
    var ballDY = 0f

    // Paddle properties
    var paddleX = screenWidth / 2f
    var paddleWidth = 200f
    var paddleHeight = 25f

    var score = 0
    var highScore = 0
    var isGameOver = false
    var gameStarted = false

    var fireSoundId = pool.load( cntxt, R.raw.hit, 1)


    fun update() {
        // Ensure the game has started
        if (isGameOver || !gameStarted) return
        highScore = pref.getInt("HIGH_SCORE", 0)

        // Ball movement
        ballX += ballDX
        ballY += ballDY

        // Collision with the screen edges
        if (ballX < ballRadius || ballX > screenWidth - ballRadius) ballDX *= -1
        if (ballY < 0) ballDY *= -1

        // Paddle collision
        val paddleTop = screenHeight - paddleHeight - 90
        if (ballX + ballRadius >= paddleX && ballX - ballRadius <= paddleX + paddleWidth) {
            if (ballY + ballRadius >= paddleTop && ballY + ballRadius <= paddleTop + paddleHeight) {
                playSound(fireSoundId)
                ballDY *= -1 // Reverse the ball's vertical movement
                ballY = paddleTop - ballRadius
                score += 1
                if (score > highScore) {
                    highScore = score
                    val editor = pref.edit()
                    editor.putInt("HIGH_SCORE",highScore)
                    editor.apply()

                }
            }
        }

        // Game over
        if (ballY > screenHeight) {
            isGameOver = true
        }
    }

    fun startGame() {
        // Start the ball at the top and move at a 45-degree angle to the right
        ballX = screenWidth / 2f
        ballY = ballRadius + 1
        val initialSpeed = 5f
        ballDX = initialSpeed / Math.sqrt(2.0).toFloat()
        ballDY = initialSpeed / Math.sqrt(2.0).toFloat()
        gameStarted = true
    }

    fun reset() {
        ballX = screenWidth / 2f
        ballY = ballRadius + 1 // Reset ball to top
        ballDX = 0f // Ball is still until game starts
        ballDY = 0f
        score = 0
        isGameOver = false
        gameStarted = false //Making sure game doesn't start auto
    }

    fun playSound(soundId : Int) {
        pool.play(soundId, 1.0f, 1.0f, 0, 0, 1.0f)
    }
}