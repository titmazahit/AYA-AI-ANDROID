package com.titmazahit.aya

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.MotionEvent
import android.view.View
import kotlin.math.sin

/**
 * Temporary procedural placeholder.
 * This is not the final approved Aya artwork or a Live2D model.
 */
class AyaStageView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var started = false
    private var frame = 0L
    private var message = "Привет! Я Ая."
    private var blink = 0f

    fun startAnimation() {
        if (!started) {
            started = true
            invalidate()
        }
    }

    fun stopAnimation() {
        started = false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        canvas.drawColor(android.graphics.Color.rgb(14, 10, 24))

        val bob = if (started) sin(frame * 0.055).toFloat() * h * 0.008f else 0f
        blink = if (started && frame % 150L in 0L..5L) 0.12f else 1f

        // soft background halo
        paint.color = 0x332D1760
        canvas.drawCircle(w * .5f, h * .38f, w * .39f, paint)

        // hair behind body
        paint.color = 0xFF171020.toInt()
        canvas.drawOval(RectF(w*.29f, h*.12f+bob, w*.71f, h*.61f+bob), paint)

        // torso and outfit
        paint.color = 0xFF24162F.toInt()
        canvas.drawRoundRect(RectF(w*.39f, h*.39f+bob, w*.61f, h*.69f+bob), w*.045f, w*.045f, paint)
        paint.color = 0xFF7B3CC7.toInt()
        canvas.drawRoundRect(RectF(w*.485f, h*.40f+bob, w*.515f, h*.66f+bob), w*.01f, w*.01f, paint)

        // arms
        paint.color = 0xFFFFD9D0.toInt()
        paint.strokeWidth = w*.035f
        paint.strokeCap = Paint.Cap.ROUND
        canvas.drawLine(w*.41f, h*.44f+bob, w*.23f, h*.59f+bob, paint)
        canvas.drawLine(w*.59f, h*.44f+bob, w*.77f, h*.59f+bob, paint)

        // skirt and legs
        paint.color = 0xFF171020.toInt()
        canvas.drawOval(RectF(w*.34f, h*.64f+bob, w*.66f, h*.76f+bob), paint)
        paint.color = 0xFFFFD9D0.toInt()
        canvas.drawRoundRect(RectF(w*.43f, h*.73f+bob, w*.48f, h*.91f+bob), w*.02f, w*.02f, paint)
        canvas.drawRoundRect(RectF(w*.52f, h*.73f+bob, w*.57f, h*.91f+bob), w*.02f, w*.02f, paint)

        // face
        paint.color = 0xFFFFD9D0.toInt()
        canvas.drawOval(RectF(w*.365f, h*.20f+bob, w*.635f, h*.46f+bob), paint)

        // fringe
        paint.color = 0xFF171020.toInt()
        val fringe = android.graphics.Path().apply {
            moveTo(w*.37f, h*.31f+bob)
            quadTo(w*.40f, h*.13f+bob, w*.51f, h*.19f+bob)
            quadTo(w*.62f, h*.12f+bob, w*.64f, h*.32f+bob)
            quadTo(w*.57f, h*.27f+bob, w*.53f, h*.34f+bob)
            quadTo(w*.48f, h*.27f+bob, w*.44f, h*.35f+bob)
            close()
        }
        canvas.drawPath(fringe, paint)

        // eyes
        paint.color = 0xFF9B54E8.toInt()
        canvas.drawOval(RectF(w*.415f, h*.315f+bob, w*.465f, h*(.315f+.045f*blink)+bob), paint)
        canvas.drawOval(RectF(w*.535f, h*.315f+bob, w*.585f, h*(.315f+.045f*blink)+bob), paint)
        paint.color = android.graphics.Color.WHITE
        canvas.drawCircle(w*.442f, h*.328f+bob, w*.008f, paint)
        canvas.drawCircle(w*.562f, h*.328f+bob, w*.008f, paint)

        // mouth
        paint.color = 0xFF9B5268.toInt()
        canvas.drawLine(w*.485f, h*.395f+bob, w*.515f, h*.395f+bob, paint)

        // caption
        paint.color = android.graphics.Color.WHITE
        paint.textAlign = Paint.Align.CENTER
        paint.textSize = w*.055f
        canvas.drawText(message, w*.5f, h*.965f, paint)

        frame++
        if (started) postInvalidateDelayed(33)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            message = when {
                event.y < height * .42f -> "Хи-хи! Ты меня заметил."
                event.x < width * .5f -> "Мне приятно!"
                else -> "Я здесь!"
            }
            invalidate()
            return true
        }
        return true
    }
}
