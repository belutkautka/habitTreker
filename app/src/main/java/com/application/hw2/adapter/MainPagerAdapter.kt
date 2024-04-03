package com.application.hw2.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.application.hw2.fragments.HabitFragment

class MainPagerAdapter(fragment: Fragment, val fragments: List<HabitFragment>) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}