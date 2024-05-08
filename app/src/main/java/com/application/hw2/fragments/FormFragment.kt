package com.application.hw2.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
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
import androidx.annotation.RequiresApi
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.application.hw2.ColorPicker
import com.application.hw2.R
import com.application.hw2.databinding.FormFragmentBinding
import com.application.hw2.enums.HabitType
import com.application.hw2.model.HabitModel
import com.application.hw2.viewModels.FormViewModel
import java.time.OffsetDateTime
import java.time.ZoneOffset

class FormFragment : Fragment() {
    private var _binding: FormFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var formViewModel: FormViewModel

    private var defaultColor: Int = 0
    private var startColor: Int = 0
    private var endColor: Int = 0
    private var type: Int = 1
    private var habitToEdit: HabitModel? = null

    private val navController: NavController by lazy {
        Navigation.findNavController(requireView())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FormFragmentBinding.inflate(inflater, container, false)
        formViewModel = ViewModelProvider(requireActivity())[FormViewModel::class.java]
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val submitButton = binding.add
        val name = binding.editName
        val description = binding.editDescription
        val priority = binding.editPriotity
        val typeGroup = binding.radioGroup
        val currentColor = binding.currentColor
        val count = binding.editCount

        habitToEdit = FormFragmentArgs.fromBundle(requireArguments()).habitToEdit

        initColor(currentColor)
        initButton(submitButton, name, count, description, priority, currentColor)
        typeGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = typeGroup.findViewById<RadioButton>(checkedId)
            if (checkedRadioButton != null && checkedRadioButton.isChecked) {
                type = if (checkedRadioButton.text.toString() == HabitType.GOOD.toString()) {
                    1
                } else {
                    0
                }
            }
        }

        if (habitToEdit != null) {
            setHabitData(
                name, count, description, priority, habitToEdit!!,
                submitButton, typeGroup, currentColor
            )
        } else {
            submitButton.text = resources.getText(R.string.add, null)
        }
        calculateColors()
    }

    fun setHabitData(
        name: EditText, count: EditText, description: EditText, priority: Spinner,
        changedHabit: HabitModel, submitButton: Button, typeGroup: RadioGroup, curentColor: View
    ) {
        name.setText(changedHabit.name, TextView.BufferType.EDITABLE)
        description.setText(changedHabit.description, TextView.BufferType.EDITABLE)
        priority.setSelection(changedHabit.priority - 1)
        count.setText(changedHabit.periodicity.toString(), TextView.BufferType.EDITABLE)
        submitButton.setText(resources.getText(R.string.save, null))
        typeGroup.forEach { view ->
            if (view is RadioButton && ((view.text == HabitType.GOOD.name && changedHabit.type == 1) ||
                        (view.text == HabitType.BAD.name && changedHabit.type == 0))
            ) {
                view.isChecked = true
                return@forEach
            }
        }
        curentColor.setBackgroundColor(changedHabit.color)
        val rgb = binding.rgb
        val hsv = binding.hsv
        rgb.text = ColorPicker.colorToRgbString(changedHabit.color)
        hsv.text = ColorPicker.colorToHsvString(changedHabit.color)
    }

    private fun initColor(currentColor: View) {
        defaultColor = resources.getColor(R.color.white, null)
        startColor = resources.getColor(R.color.orange, null)
        endColor = resources.getColor(R.color.gradient_purple, null)

        val defaultButton = binding.toWhite
        currentColor.setBackgroundColor(defaultColor)
        val rgb = binding.rgb
        val hsv = binding.hsv
        rgb.text = ColorPicker.colorToRgbString(defaultColor)
        hsv.text = ColorPicker.colorToHsvString(defaultColor)
        defaultButton.setOnClickListener {
            currentColor.setBackgroundColor(defaultColor)
            rgb.text = ColorPicker.colorToRgbString(defaultColor)
            hsv.text = ColorPicker.colorToHsvString(defaultColor)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initButton(
        submitButton: Button, name: EditText, count: EditText,
        description: EditText, priority: Spinner, currentColor: View
    ) {
        submitButton.setOnClickListener {
            val isValidate = validateEditView(name) && validateEditView(description)
                    && validateEditView(count)
            if (isValidate) {
                val habit = HabitModel(
                    name = name.text.toString(),
                    description = description.text.toString(),
                    priority = priority.selectedItem.toString().toInt(),
                    type = type,
                    periodicity = count.text.toString().toInt(),
                    color = getBackgroundColor(currentColor),
                    date = OffsetDateTime.now(ZoneOffset.UTC).toEpochSecond().toInt(),
                    doneDates = mutableListOf()
                )
                if (habitToEdit != null) {
                    habit.id = habitToEdit!!.id
                    habit.doneDates = habitToEdit!!.doneDates
                }
                formViewModel.insertHabit(habit, habitToEdit == null)
                navController.popBackStack()
            }
        }
    }

    private fun validateEditView(view: EditText): Boolean {
        if (view.text.toString().isEmpty()) {
            view.error = resources.getText(R.string.error, null)
            return false
        }
        return true
    }

    private fun getBackgroundColor(view: View): Int {
        val drawable = view.background
        return if (drawable is ColorDrawable) {
            drawable.color
        } else {
            Color.TRANSPARENT
        }
    }

    private fun calculateColors() {
        super.onResume()
        val rgb = binding.rgb
        val hsv = binding.hsv
        val linearLayout = binding.gradient
        linearLayout.post {
            val w = linearLayout.width;
            val currentColor = binding.currentColor
            for (i in 0 until linearLayout.childCount) {
                val childView = linearLayout.getChildAt(i)
                childView.tag = ColorPicker.getColor(childView, w, startColor, endColor)
                childView.setOnClickListener { view ->
                    val colorTag = view.tag.toString().toInt()
                    currentColor.setBackgroundColor(colorTag)
                    rgb.text = ColorPicker.colorToRgbString(colorTag)
                    hsv.text = ColorPicker.colorToHsvString(colorTag)
                }
            }
        }
    }
}