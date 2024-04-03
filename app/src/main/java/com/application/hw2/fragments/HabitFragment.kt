package com.application.hw2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.application.hw2.R
import com.application.hw2.adapter.HabitAdapter
import com.application.hw2.databinding.HabitFragmentBinding
import com.application.hw2.db.HabitsList
import com.application.hw2.enums.HabitType

class HabitFragment : Fragment() {
    private var _binding: HabitFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var habitType: HabitType

    private lateinit var adapter: HabitAdapter
    private lateinit var recyclerView: RecyclerView

    companion object {
        fun newInstance() = HabitFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.apply {
            habitType = this.getSerializable(MainFragment.BUNDLE_KEY) as HabitType
        }

        _binding = HabitFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        recyclerView = binding.recyclerView
        adapter = HabitAdapter(onHabitClickListener = { habit, position ->
            val action = MainFragmentDirections.actionFragmentMainToFragmentAddEdit()
            action.habitToEdit = habit
            action.position = position
            action.label = getString(R.string.label_edit)
            Navigation.findNavController(requireView()).navigate(action)
        })

        recyclerView.adapter = adapter
        adapter.submitList(HabitsList.selectHabitsByType(habitType))
    }
}