package com.application.hw2.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.application.hw2.FormActivity
import com.application.hw2.MainActivity
import com.application.hw2.databinding.ItemHabitLayoutBinding
import com.application.hw2.model.HabitModel

class HabitAdapter(private val context: Context) : RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    var habitList = ArrayList<HabitModel>()
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
        holder.binding.priority.text = habitList[position].getStars()
        holder.itemView.setBackgroundColor( habitList[position].color)
        habitList[position].position = position
        Log.d(position.toString(), "хммммм")
        holder.itemView.setOnClickListener {
            val intent = Intent(context, FormActivity::class.java)
            intent.putExtra("HABIT_CHANGE", habitList[position])
            context.startActivity(intent)
        }
    }
}