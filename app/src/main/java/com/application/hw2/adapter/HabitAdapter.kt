package com.application.hw2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.application.data.model.HabitModel
import com.application.hw2.databinding.ItemHabitLayoutBinding

class HabitAdapter(val onHabitClickListener: (HabitModel) -> Unit,
                   val onHabitDeleteClickListener: (HabitModel) -> Unit,
                   val onHabitDoneClickListener:  (HabitModel) -> Unit) :
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
        holder.binding.period.text = habit.period
        holder.binding.priority.text = habit.stars
        holder.itemView.setBackgroundColor(habit.color)
        holder.itemView.setOnClickListener {
            onHabitClickListener(habit)
        }
        holder.binding.delete.setOnClickListener{
            onHabitDeleteClickListener(habit)
        }
        holder.binding.add.setOnClickListener{
            onHabitDoneClickListener(habit)
        }
    }
}