package com.application.hw2.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import com.application.hw2.ColorPicker
import com.application.hw2.R
import com.application.hw2.databinding.FormFragmentBinding
import com.application.hw2.db.HabitsList
import com.application.hw2.enums.Keys
import com.application.hw2.model.HabitModel

class FormFragment() : Fragment() {
    private var _binding: FormFragmentBinding? = null
    private val binding get() = _binding!!

    private var position: Int = 0
    private var habit: HabitModel? = null

    var defaultColor: Int = 0
    var startColor: Int = 0
    var endColor: Int = 0
    var changed: Boolean = false
    var type: String = ""

    companion object {
        fun newInstance() = FormFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.apply {
            position = this.getInt(Keys.HABIT_POSITION.name)
            habit = this.getSerializable(Keys.HABIT_TO_CHANGE.name) as? HabitModel
        }

        _binding = FormFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val submitButton = binding.add
        val name = binding.editName
        val description = binding.editDescription
        val priority = binding.editPriotity
        val typeGroup = binding.radioGroup
        val curentColor = binding.currentColor
        val count = binding.editCount
        val period = binding.editPeriod
        type = "GOOD"

        init(curentColor)
        initButton(submitButton, name, period, count, description, priority, curentColor)
        typeGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = typeGroup.findViewById<RadioButton>(checkedId)
            if (checkedRadioButton != null && checkedRadioButton.isChecked) {
                type = checkedRadioButton.text.toString()
            }
        }

        changed = false
        if (habit != null) {
            changed = true
            setHabitData(
                name,
                period,
                count,
                description,
                priority,
                habit!!,
                submitButton,
                typeGroup,
                curentColor
            )
        } else {
            submitButton.text = resources.getText(R.string.add, null)
        }
        calculateColors()
    }

    fun setHabitData(
        name: EditText, period: EditText, count: EditText, description: EditText, priority: Spinner,
        changedHabit: HabitModel, submitButton: Button, typeGroup: RadioGroup, curentColor: View
    ) {
        name.setText(changedHabit.name, TextView.BufferType.EDITABLE)
        description.setText(changedHabit.description, TextView.BufferType.EDITABLE)
        priority.setSelection(changedHabit.priority - 1)
        count.setText(changedHabit.count.toString(), TextView.BufferType.EDITABLE)
        period.setText(changedHabit.periodicity, TextView.BufferType.EDITABLE)
        submitButton.setText(resources.getText(R.string.save, null))
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

        val defaultButton = binding.toWhite
        defaultButton.setOnClickListener {
            val rgb = binding.rgb
            val hsv = binding.hsv
            curentColor.setBackgroundColor(defaultColor)
            rgb.text = ColorPicker.colorToRgbString(defaultColor)
            hsv.text = ColorPicker.colorToHsvString(defaultColor)
        }
    }

    fun initButton(
        submitButton: Button, name: EditText, period: EditText, count: EditText,
        description: EditText, priority: Spinner, curentColor: View
    ) {
        submitButton.setOnClickListener {
            val isValidate =
                validateEditView(name) && validateEditView(description) && validateEditView(count) && validateEditView(
                    period
                )
            if (isValidate) {
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
                val fragment =
                    parentFragmentManager.findFragmentByTag("MAIN_FRAGMENT") as MainFragment
                fragment.updateFragments()
                parentFragmentManager.beginTransaction()
                    .show(fragment)
                    .remove(this)
                    .commit()

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

    fun calculateColors() {
        super.onResume()
        val rgb = binding.rgb
        val hsv = binding.hsv
        val linearLayout = binding.gradient
        linearLayout.post {
        val w = linearLayout.width;
        val curentColor = binding.currentColor
        for (i in 0 until linearLayout.childCount) {
            val childView = linearLayout.getChildAt(i)
            childView.tag = ColorPicker.getColor(childView, w, startColor, endColor)
            childView.setOnClickListener { view ->
                val colorTag = view.tag.toString().toInt()
                curentColor.setBackgroundColor(colorTag)
                rgb.text = ColorPicker.colorToRgbString(colorTag)
                hsv.text = ColorPicker.colorToHsvString(colorTag)
            }
        }}
        rgb.text = ColorPicker.colorToRgbString(defaultColor)
        hsv.text = ColorPicker.colorToRgbString(defaultColor)
    }
}