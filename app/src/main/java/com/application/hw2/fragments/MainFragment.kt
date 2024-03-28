package com.application.hw2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.application.hw2.R
import com.application.hw2.databinding.MainFragmentBinding
import com.application.hw2.db.HabitsList
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment(R.layout.main_fragment) {
    private lateinit var viewPager: ViewPager2
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val tabTitle = listOf("GOOD" ,"BAD")

    private val fragments = ArrayList<HabitFragment>();

    private val navController: NavController by lazy {
        Navigation.findNavController(requireView())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        if (fragments.isEmpty()) {
            val fragmentGOOD = HabitFragment.newInstance()
            fragmentGOOD.arguments = Bundle().apply {
                putSerializable(BUNDLE_KEY, HabitType.GOOD)
            }
            fragments.add(fragmentGOOD)
            val fragmentBAD = HabitFragment.newInstance()
            fragmentBAD.arguments = Bundle().apply {
                putSerializable(BUNDLE_KEY, HabitType.BAD)
            }
            fragments.add(fragmentBAD)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab: FloatingActionButton = binding.fabButton
//        fab.setOnClickListener {
//            val fragment = FormFragment.newInstance()
//            Log.d("TAG", this.tag.toString())
//            parentFragmentManager
//                .beginTransaction()
//                .hide(this)
//                .add(R.id.mainFrameLayout, fragment, "FORM_FRAGMENT")// TODO вынести теги
//                .commit()
//        }

        fab.setOnClickListener {
            val action = MainFragmentDirections.actionFragmentMainToFragmentAddEdit(getString(R.string.label_add))
            navController.navigate(action)
        }

        viewPager = binding.MainViewPager
        HabitsList.init()
        val pagerAdapter = MainPagerAdapter(this, fragments)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tab,viewPager){ tab,pos -> tab.text = tabTitle[pos]}.attach()
    }

    fun updateFragments() {
        fragments[0].update()
        fragments[1].update()
        super.onResume()
    }

    companion object {
//        const val FRAGMENT_TAG = "MAIN_FRAGMENT"
        const val BUNDLE_KEY = "HABIT_TYPE_KEY"

//        fun newInstance() = MainFragment()
    }

    enum class HabitType {
        GOOD,
        BAD
    }

    private class MainPagerAdapter(fragment: Fragment, val fragments: List<HabitFragment>) :
        FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = fragments.size

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }
}