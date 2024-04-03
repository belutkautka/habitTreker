package com.application.hw2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.application.hw2.adapter.HabitAdapter
import com.application.hw2.databinding.HabitFragmentBinding
import com.application.hw2.db.HabitsList

class HabitFragment() : Fragment() {
    private var _binding: HabitFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var habitType: MainFragment.HabitType

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

    fun update() {
        if (::adapter.isInitialized) {
            adapter.submitList(
                HabitsList.selectHabitsByType(
                    type = when (habitType) {
                        MainFragment.HabitType.GOOD -> "GOOD"
                        MainFragment.HabitType.BAD -> "BAD"
                    }
                )
            )
        }
    }

    private fun initial() {
        recyclerView = binding.recyclerView
        adapter = HabitAdapter(this, onHabitClickListener = { habit, position ->
            val action = MainFragmentDirections.actionFragmentMainToFragmentAddEdit(
                arg1 = habit, arg2 = position
            )

            Navigation.findNavController(requireView()).navigate(action)
        })
        recyclerView.adapter = adapter
        adapter.submitList(
            HabitsList.selectHabitsByType(
                type = when (habitType) {
                    MainFragment.HabitType.GOOD -> "GOOD"
                    MainFragment.HabitType.BAD -> "BAD"
                }
            )
        )

    }
}