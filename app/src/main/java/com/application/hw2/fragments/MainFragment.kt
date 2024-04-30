package com.application.hw2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.application.hw2.R
import com.application.hw2.adapter.MainPagerAdapter
import com.application.hw2.databinding.MainFragmentBinding
import com.application.hw2.enums.HabitType
import com.application.hw2.viewModels.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment(R.layout.main_fragment) {
    private lateinit var viewPager: ViewPager2
    private lateinit var mainViewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val tabTitle = HabitType.values()

    private val fragments = ArrayList<HabitFragment>();

    private val navController: NavController by lazy {
        Navigation.findNavController(requireView())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        if (fragments.isEmpty()) {
            val fragmentGOOD = HabitFragment.newInstance()
            fragmentGOOD.arguments = Bundle().apply {
                putInt(BUNDLE_KEY, 1)
            }
            fragments.add(fragmentGOOD)
            val fragmentBAD = HabitFragment.newInstance()
            fragmentBAD.arguments = Bundle().apply {
                putInt(BUNDLE_KEY, 0)
            }
            fragments.add(fragmentBAD)
        }

        val bottomSheet = binding.bottomSheet.bottomSheetMainFragment;
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        // Установка высоты BottomSheet до первого TextView
        bottomSheet.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                bottomSheet.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val textViewHeight = binding.bottomSheet.findAndSortTextView.height
                bottomSheetBehavior.setPeekHeight(textViewHeight, false)
            }
        })

        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fab: FloatingActionButton = binding.fabButton
        fab.setOnClickListener {
            val action = MainFragmentDirections.actionFragmentMainToFragmentAddEdit()
            action.label = getString(R.string.label_add)
            navController.navigate(action)
        }


        val upButton = binding.bottomSheet.up
        val downButton = binding.bottomSheet.down
        upButton.setOnClickListener {
            mainViewModel.sortByPriority(false)
        }
        downButton.setOnClickListener {
            mainViewModel.sortByPriority(true)
        }

        binding.bottomSheet.find.setOnClickListener {
            mainViewModel.searchHabits(binding.bottomSheet.searchName.text.toString())
        }

        viewPager = binding.MainViewPager
        val pagerAdapter = MainPagerAdapter(this, fragments)
        viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tab, viewPager) { tab, pos ->
            tab.text = tabTitle[pos].toString()
        }.attach()
    }

    companion object {
        const val BUNDLE_KEY = "HABIT_TYPE_KEY"
    }
}