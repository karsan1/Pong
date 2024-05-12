package com.example.project5

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import kotlin.math.max
import kotlin.math.min

class GameView(context: Context) : View(context) {
    private val paint = Paint()
    var game = Pong(context.resources.displayMetrics.widthPixels, context.resources.displayMetrics.heightPixels, context)

    init {
        paint.textSize = 60f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the ball
        paint.color = android.graphics.Color.BLACK
        canvas.drawCircle(game.ballX, game.ballY, game.ballRadius, paint)

        // Draw the paddle
        val paddleTop = height - game.paddleHeight - 90
        paint.color = android.graphics.Color.BLACK
        canvas.drawRect(
            game.paddleX,
            paddleTop,
            game.paddleX + game.paddleWidth,
            paddleTop + game.paddleHeight,
            paint
        )

        paint.color = android.graphics.Color.BLACK


        if (game.isGameOver) {
            canvas.drawText("Game Over", width / 2f - 100f, height / 2f, paint)
            canvas.drawText("Score: ${game.score}", 50f, 100f, paint)
            canvas.drawText("High Score: ${game.highScore}", width - 400f, 100f, paint)

        }
    }


    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!game.gameStarted) {
                    game.startGame()
                    invalidate()
                } else if (game.isGameOver) {
                    game.reset()
                    game.startGame()
                    invalidate()
                }
                updatePaddlePosition(event.x)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                if (game.gameStarted && !game.isGameOver) {
                    updatePaddlePosition(event.x)
                }
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun updatePaddlePosition(touchX: Float) {
        game.paddleX = touchX - game.paddleWidth / 2
        game.paddleX = max(0f, min(game.paddleX, width - game.paddleWidth))
        invalidate()
    }


    fun update() {
        if (!game.isGameOver) {
            game.update()
            invalidate()
        }
    }
    fun getHighScore(): Int {
        return game.highScore
    }
}