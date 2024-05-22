package com.application.hw2.model

//extention функция*
class HabitConverter {
    companion object{
    fun HabitFromServerToHabit(habitFromServer: HabitFromServer): HabitModel {
        return HabitModel(
            id = habitFromServer.uid.toString(),
            name = habitFromServer.title,
            description = habitFromServer.description,
            priority = habitFromServer.priority,
            type = habitFromServer.type,
            count = habitFromServer.count,
            periodicity = habitFromServer.frequency,
            color = habitFromServer.color,
            date = habitFromServer.date,
            doneDates = habitFromServer.doneDates
        )
    }

    fun HabitToHabitFromServer(habit: HabitModel): HabitFromServer {
        return HabitFromServer(
            uid = habit.id,
            title = habit.name,
            description = habit.description,
            priority = habit.priority,
            type = habit.type,
            count = habit.count,
            frequency = habit.periodicity,
            color = habit.color,
            date = habit.date,
            doneDates = habit.doneDates
        )
    }
    }
}