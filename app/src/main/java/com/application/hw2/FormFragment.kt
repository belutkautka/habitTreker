package com.application.hw2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.application.hw2.databinding.FormFragmentBinding
import com.application.hw2.databinding.HabitFragmentBinding
import com.application.hw2.enums.Keys
import com.application.hw2.model.HabitModel

class FormFragment() : Fragment() {
    private var _binding: FormFragmentBinding? = null
    private val binding get() = _binding!!

    private var position: Int = 0
    private lateinit var habit: HabitModel

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
            habit = this.getSerializable(Keys.HABIT_TO_CHANGE.name) as HabitModel
        }

        _binding = FormFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}