package com.application.hw2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.application.hw2.databinding.ItemHabitLayoutBinding
import com.application.hw2.model.HabitModel

class HabitAdapter : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    var habitList = emptyList<HabitModel>()

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

    override fun getItemCount(): Int {
        return habitList.size
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.binding.name.text = habitList[position].name
        holder.binding.description.text = habitList[position].description
        holder.binding.type.text =  habitList[position].type
        holder.binding.period.text = habitList[position].period
    }
}