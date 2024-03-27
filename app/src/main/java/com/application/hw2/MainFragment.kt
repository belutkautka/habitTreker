package com.application.hw2.com.application.hw2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.application.hw2.FormFragment
import com.application.hw2.HabitFragment
import com.application.hw2.R
import com.application.hw2.databinding.MainFragmentBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private var _binding: MainFragmentBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab: FloatingActionButton = binding.fabButton
        fab.setOnClickListener {
            val fragment = FormFragment.newInstance()

            parentFragmentManager
                .beginTransaction()
                .add(R.id.mainFrameLayout, fragment, "FORM_FRAGMENT")// TODO вынести теги
                .commit()
        }

        viewPager = binding.MainViewPager

        val pagerAdapter = MainPagerAdapter(this)
        viewPager.adapter = pagerAdapter
    }

    companion object {
        const val FRAGMENT_TAG = "MAIN_FRAGMENT"
        const val BUNDLE_KEY = "HABIT_TYPE_KEY"

        fun newInstance() = MainFragment()
    }

    enum class HabitType {
        GOOD,
        BAD
    }

    private class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            val type = when (position) {
                1 -> HabitType.GOOD
                2 -> HabitType.BAD
                else -> {
                    HabitType.GOOD
                }
            }

            val fragment = HabitFragment.newInstance()
            fragment.arguments = Bundle().apply {
                putSerializable(BUNDLE_KEY, type)
            }

            return fragment
        }
    }
}