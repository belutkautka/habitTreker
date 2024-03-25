package com.application.hw2

import android.graphics.Color
import android.view.View

object ColorPicker {
    fun getColor(view: View, w: Int, startColor: Int, endColor: Int): Int {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        val viewLeft = location[0]
        val viewWidth = view.width
        val v1 = viewLeft + viewWidth / 2
        return interpolateColor(startColor, endColor, (v1.toFloat() / w))
    }

    fun colorToRgbString(color: Int): String {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return "RGB: ($red, $green, $blue)"
    }

    fun colorToHsvString(color: Int): String {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        return "HSV: ${hsv[0].toInt()}°, ${hsv[1].toInt()}%, ${hsv[2].toInt()}%"
    }

    fun interpolateColor(color1: Int, color2: Int, fraction: Float): Int {
        val a = (Color.alpha(color1) * (1 - fraction) + Color.alpha(color2) * fraction).toInt()
        val r = (Color.red(color1) * (1 - fraction) + Color.red(color2) * fraction).toInt()
        val g = (Color.green(color1) * (1 - fraction) + Color.green(color2) * fraction).toInt()
        val b = (Color.blue(color1) * (1 - fraction) + Color.blue(color2) * fraction).toInt()
        return Color.argb(a, r, g, b)
    }
}