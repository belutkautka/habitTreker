package com.application.hw2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.application.hw2.adapter.HabitAdapter
import com.application.hw2.com.application.hw2.MainFragment
import com.application.hw2.databinding.HabitFragmentBinding
import com.application.hw2.db.HabitsList
import com.application.hw2.enums.Keys
import com.application.hw2.model.HabitModel

class HabitFragment() : Fragment() {
    private var _binding: HabitFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var habitType : MainFragment.HabitType

    lateinit var adapter: HabitAdapter
    lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = HabitFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = arguments?.apply {
            habitType = this.getSerializable(MainFragment.BUNDLE_KEY) as MainFragment.HabitType
        }

        _binding = HabitFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initial()
    }

    override fun onResume() {
        if (HabitsList.changed) {
            adapter.submitList(HabitsList.selectAllHabits())
        }
        super.onResume()
    }

    private fun initial() {
        recyclerView = binding.recyclerView
        adapter = HabitAdapter { habit: HabitModel, position: Int ->

            val fragment = FormFragment.newInstance()
            fragment.arguments = Bundle().apply {
                putInt(Keys.HABIT_POSITION.name, position)
                putSerializable(Keys.HABIT_TO_CHANGE.name, habit)
            }

            parentFragmentManager
                .beginTransaction()
                .add(R.id.mainFrameLayout, fragment, "HABIT_FRAGMENT")
                .commit()
        }
        recyclerView.adapter = adapter
        adapter.submitList(HabitsList.selectHabitsByType(
            type = when (habitType) {
                MainFragment.HabitType.GOOD -> ""
                MainFragment.HabitType.BAD -> ""
            }
        ))

    }
}