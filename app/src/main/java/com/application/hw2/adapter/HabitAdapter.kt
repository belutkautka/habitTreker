package com.application.hw2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.application.hw2.R
import com.application.hw2.databinding.ItemHabitLayoutBinding
import com.application.hw2.fragments.HabitFragment
import com.application.hw2.model.HabitModel

class HabitAdapter(val context: HabitFragment, ) :
    ListAdapter<HabitModel, HabitAdapter.HabitViewHolder>(MyItemDiffCallback()) {
    class HabitViewHolder(val binding: ItemHabitLayoutBinding) : ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        return HabitViewHolder(
            ItemHabitLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    class MyItemDiffCallback : DiffUtil.ItemCallback<HabitModel>() {
        override fun areItemsTheSame(oldItem: HabitModel, newItem: HabitModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: HabitModel, newItem: HabitModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = getItem(position)
        holder.binding.name.text = habit.name
        holder.binding.description.text = habit.description
        holder.binding.type.text = habit.type
        holder.binding.period.text = habit.period
        holder.binding.priority.text = habit.getStars()
//        holder.itemView.setOnClickListener {
//            val action = MainFragmentDirections.actionFragmentMainToFragmentAddEdit(
//                context.resources.getString(R.string.label_edit)
//            )
//            action.habitToEdit = habit
//            action.position = position
//
//            Navigation.findNavController(it).navigate(action)
//        }

    }
}