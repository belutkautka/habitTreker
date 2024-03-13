package com.application.hw2

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
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
}