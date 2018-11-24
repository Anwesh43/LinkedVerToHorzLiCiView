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

