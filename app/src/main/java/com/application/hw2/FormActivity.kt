package com.application.hw2

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import com.application.hw2.model.HabitModel

class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_layout)
        val submitButton = findViewById<Button>(R.id.add)
        val name = findViewById<EditText>(R.id.editName)
        val description = findViewById<EditText>(R.id.editDescription)
        val priority = findViewById<Spinner>(R.id.editPriotity)
        val typeGroup = findViewById<RadioGroup>(R.id.radioGroup)
        var type = "Учеба"
        typeGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = typeGroup.findViewById<RadioButton>(checkedId)
            if (checkedRadioButton != null && checkedRadioButton.isChecked) {
                type = checkedRadioButton.text.toString()
            }
        }
        val count = findViewById<EditText>(R.id.editCount)
        val period = findViewById<EditText>(R.id.editPeriod)
        val changedHabit = intent.getSerializableExtra("HABIT_CHANGE") as? HabitModel
        var changed = false
        if (changedHabit != null) {
            changed = true
            name.text = Editable.Factory.getInstance().newEditable(changedHabit.name)
            description.text = Editable.Factory.getInstance().newEditable(changedHabit.description)
            priority.setSelection(changedHabit.priority - 1)
            count.text = Editable.Factory.getInstance().newEditable(changedHabit.count.toString())
            period.text = Editable.Factory.getInstance().newEditable(changedHabit.periodicity)
            submitButton.text = "Сохранить изменения"
            typeGroup.forEach { view ->
                if (view is RadioButton && view.text == changedHabit.type) {
                    view.isChecked = true
                    return@forEach
                }
            }
        } else {
            submitButton.text = "Добавить"
        }
        submitButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                val habit = HabitModel(
                    name.text.toString(), description.text.toString(),
                    priority.selectedItem.toString().toInt(),
                    type, count.text.toString().toInt(), period.text.toString()
                )
                habit.position = changedHabit?.position ?: 0
                if (changed) {
                    putExtra("HABIT_CHANGED", habit)
                } else {
                    putExtra("HABIT_CREATE", habit)
                }
            }
            startActivity(intent)
        }




    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        val linearLayout = findViewById<LinearLayout>(R.id.gradient)
        val view = findViewById<View>(R.id.colorView)
        val location = IntArray(2)
        view.getLocationOnScreen(location)
// Получаем размеры и позиции
        val viewLeft = location[0]
        val viewWidth = view.width
        val v1 = viewLeft+viewWidth/2


        val w = linearLayout.width;
        val startColor = Color.parseColor("#FFD78C") // Оранжевый
        val endColor = Color.parseColor("#CD96FF")
        Log.d("color",colorToRgbString(interpolateColor(startColor, endColor,
            (v1/w).toFloat()
        )))
    }
    fun pointBetweenColors(from: Float, to: Float, percent: Float): Float =
        from + percent * (to - from)

    fun interpolateColor(color1: Int, color2: Int, fraction: Float): Int {
        val a = (Color.alpha(color1) * (1 - fraction) + Color.alpha(color2) * fraction).toInt()
        val r = (Color.red(color1) * (1 - fraction) + Color.red(color2) * fraction).toInt()
        val g = (Color.green(color1) * (1 - fraction) + Color.green(color2) * fraction).toInt()
        val b = (Color.blue(color1) * (1 - fraction) + Color.blue(color2) * fraction).toInt()
        return Color.argb(a, r, g, b)
    }
    fun colorToRgbString(color: Int): String {
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)
        return "RGB: ($red, $green, $blue)"
    }
}