package com.anwesh.uiprojects.verttohorzliciview

/**
 * Created by anweshmishra on 24/11/18.
 */

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import android.view.MotionEvent
import android.graphics.Color
import android.app.Activity
import android.content.Context
import android.graphics.Path

val nodes : Int = 5
val lines : Int = 5
val color : Int = Color.parseColor("#4A148C")
val sizeFactor : Int = 3
val strokeFactor : Int = 60
val scDiv : Double = 0.51
val scGap : Float = 0.05f

fun Int.getInverse() : Float = 1f / this

fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.getInverse(), Math.max(0f, this - i * n.getInverse())) * n

fun Float.factorValue() : Float = Math.floor(this / scDiv).toFloat()

fun Float.mirrorValue(a : Int, b : Int) : Float = (1 - factorValue()) * a.getInverse() + b.getInverse() * factorValue()

fun Float.updateScale(dir : Float, a : Int, b : Int) : Float = dir * scGap * mirrorValue(a, b)

fun Canvas.drawVTHNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    val gap : Float = w / (nodes + 1)
    val size : Float = gap / sizeFactor
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = Math.min(w, h) / strokeFactor
    paint.color = color
    paint.strokeCap = Paint.Cap.ROUND
    val sc1 : Float = scale.divideScale(0, 2)
    val sc2 : Float = scale.divideScale(1, 2)
    save()
    translate(gap * (i + 1), h / 2)
    rotate(90f * sc2)
    val path : Path = Path()
    path.addCircle(0f, 0f, size, Path.Direction.CW)
    clipPath(path)
    drawPath(path, paint)
    val yGap : Float = (2 * size) / (lines + 2)
    var y : Float = size - yGap
    drawLine(-size, y, size, y, paint)
    for (j in 0..(lines - 1)) {
        val sc : Float = sc1.divideScale(j, lines)
        y -= yGap * sc
        save()
        translate(0f, y)
        drawLine(-size, 0f, size, 0f, paint)
        restore()
    }
    restore()
}

class VertToHorzLiCiView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float =0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += 0.05f * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdatung(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }

        data class Animator(var view : View, var animated : Boolean = false) {
            fun animate(cb : () -> Unit) {
                if (animated) {
                    cb()
                    try {
                        Thread.sleep(50)
                        view.invalidate()
                    } catch(ex : Exception) {

                    }
                }
            }

            fun start() {
                if (!animated) {
                    animated = true
                    view.postInvalidate()
                }
            }

            fun stop() {
                if (animated) {
                    animated = false
                }
            }
        }
    }
}