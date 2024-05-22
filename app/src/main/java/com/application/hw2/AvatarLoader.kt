package com.application.hw2

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import de.hdodenhof.circleimageview.CircleImageView

object AvatarLoader {
    fun loadAvatar(imageView: CircleImageView) {
        Picasso
            .with(imageView.context)
            .load("https://img.freepik.com/premium-photo/there-is-a-cat-that-is-flying-in-the-sky-with-its-paws-generative-ai_927978-67711.jpg?w=900")
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .transform(CircleTransform())
            .into(imageView)
    }
}

// Класс для преобразования изображения в круглую форму
class CircleTransform : Transformation {
    override fun key(): String {
        return "circle"
    }

    override fun transform(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)
        val x = (source.width - size) / 2
        val y = (source.height - size) / 2
        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }
        val bitmap = Bitmap.createBitmap(size, size, source.config)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true
        val radius = size / 2f
        canvas.drawCircle(radius, radius, radius, paint)
        squaredBitmap.recycle()
        return bitmap
    }
}
