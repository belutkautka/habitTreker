package com.application.hw2

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import com.application.hw2.db.HabitsList
import com.application.hw2.enums.Keys
import com.application.hw2.model.HabitModel

class FormActivity : AppCompatActivity() {

    var defaultColor: Int = 0
    var startColor: Int = 0
    var endColor: Int = 0
    var changed: Boolean = false
    var type: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form_layout)
        val submitButton = findViewById<Button>(R.id.add)
        val name = findViewById<EditText>(R.id.editName)
        val description = findViewById<EditText>(R.id.editDescription)
        val priority = findViewById<Spinner>(R.id.editPriotity)
        val typeGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val curentColor = findViewById<View>(R.id.currentColor)
        val count = findViewById<EditText>(R.id.editCount)
        val period = findViewById<EditText>(R.id.editPeriod)
        type = resources.getText(R.string.default_type, null).toString()

        init(curentColor)
        initButton(submitButton, name, period, count, description, priority, curentColor)
        typeGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = typeGroup.findViewById<RadioButton>(checkedId)
            if (checkedRadioButton != null && checkedRadioButton.isChecked) {
                type = checkedRadioButton.text.toString()
            }
        }
        val changedHabit = intent.getSerializableExtra(Keys.HABIT_TO_CHANGE.name) as? HabitModel
       changed = false
        if (changedHabit != null) {
            changed = true
            setHabitData(name, period, count, description, priority, changedHabit, submitButton, typeGroup, curentColor)
        } else {
            submitButton.text = resources.getText(R.string.add, null)
        }
    }

    fun setHabitData(
        name: EditText, period: EditText, count: EditText, description: EditText, priority: Spinner,
        changedHabit: HabitModel, submitButton: Button, typeGroup: RadioGroup, curentColor: View
    ) {
        name.text = Editable.Factory.getInstance().newEditable(changedHabit.name)
        description.text = Editable.Factory.getInstance().newEditable(changedHabit.description)
        priority.setSelection(changedHabit.priority - 1)
        count.text = Editable.Factory.getInstance().newEditable(changedHabit.count.toString())
        period.text = Editable.Factory.getInstance().newEditable(changedHabit.periodicity)
        submitButton.text = resources.getText(R.string.save, null)
        typeGroup.forEach { view ->
            if (view is RadioButton && view.text == changedHabit.type) {
                view.isChecked = true
                return@forEach
            }
        }
        curentColor.setBackgroundColor(changedHabit.color)
    }

    fun init(curentColor: View) {
        defaultColor = resources.getColor(R.color.white, null)
        startColor = resources.getColor(R.color.orange, null)
        endColor = resources.getColor(R.color.gradient_purple, null)

        val defaultButton = findViewById<Button>(R.id.toWhite)
        defaultButton.setOnClickListener {
            val rgb = findViewById<TextView>(R.id.rgb)
            val hsv = findViewById<TextView>(R.id.hsv)
            curentColor.setBackgroundColor(defaultColor)
            rgb.text = ColorPicker.colorToRgbString(defaultColor)
            hsv.text = ColorPicker.colorToHsvString(defaultColor)
        }
    }

    fun initButton(submitButton: Button, name: EditText, period: EditText, count: EditText,
                   description: EditText, priority: Spinner, curentColor: View){
        val position = intent.getIntExtra(Keys.HABIT_POSITION.name, 0)
        submitButton.setOnClickListener {
            if (validateEditView(name) && validateEditView(description) && validateEditView(count) && validateEditView(period
            )
            ) {
                val habit = HabitModel(
                    name.text.toString(), description.text.toString(),
                    priority.selectedItem.toString().toInt(),
                    type, count.text.toString().toInt(), period.text.toString()
                )
                habit.color = getBackgroundColor(curentColor)
                if (changed) {
                    HabitsList.insertIntoPosition(habit, position)
                } else {
                    HabitsList.insertToEnd(habit)
                }
                finish()
            }
        }
    }

    fun validateEditView(view: EditText): Boolean {
        if (view.text.toString().isEmpty()) {
            view.error = resources.getText(R.string.error, null)
            return false
        }
        return true
    }

    fun getBackgroundColor(view: View): Int {
        val drawable = view.background
        return if (drawable is ColorDrawable) {
            drawable.color
        } else {
            Color.TRANSPARENT
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        val rgb = findViewById<TextView>(R.id.rgb)
        val hsv = findViewById<TextView>(R.id.hsv)
        val linearLayout = findViewById<LinearLayout>(R.id.gradient)
        val w = linearLayout.width;
        val curentColor = findViewById<View>(R.id.currentColor)
        for (i in 0 until linearLayout.childCount) {
            val childView = linearLayout.getChildAt(i)
            childView.tag = ColorPicker.getColor(childView, w, startColor, endColor)
            childView.setOnClickListener { view ->
                val colorTag = view.tag.toString().toInt()
                curentColor.setBackgroundColor(colorTag)
                rgb.text = ColorPicker.colorToRgbString(colorTag)
                hsv.text = ColorPicker.colorToHsvString(colorTag)
            }
        }
        rgb.text = ColorPicker.colorToRgbString(defaultColor)
        hsv.text = ColorPicker.colorToRgbString(defaultColor)
    }
}