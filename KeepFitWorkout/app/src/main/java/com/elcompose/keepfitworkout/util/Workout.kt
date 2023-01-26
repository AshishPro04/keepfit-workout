package com.elcompose.keepfitworkout.util

import android.content.Context
import androidx.annotation.DrawableRes
import com.elcompose.keepfitworkout.R

data class Workout(val name:String, @DrawableRes val id: Int, val exercises: List<Exercise>) {
    fun getDuration(): SimpleTime {
        var totalDuration = 0
        exercises.forEach { exercise ->
            totalDuration += exercise.duration
        }
        return SimpleTime.getTimeFromSeconds(totalDuration)
    }
}

fun Workout.getExerciseNames(): List<String>{
    return this.exercises.map {
        exercise ->  exercise.name
    }
}


fun getWorkouts(context: Context): List<Workout> {
    context.run {
        val jumpingJacks = Exercise(getString(R.string.jumping_jacks), 30, 1)
        val wallSit = Exercise(getString(R.string.wall_sit),30,1)
        val pushUp = Exercise(getString(R.string.push_ups),  30, 10)
        val crunches = Exercise(getString(R.string.crunches), 30, 10)
        val stepUps = Exercise(getString(R.string.step_ups), 30, 10)
        val squats = Exercise(getString(R.string.squats), 30, 10)
        val tricepDips = Exercise(getString(R.string.tricep_dips), 30, 10)
        val plank = Exercise(getString(R.string.plank), 30, 1)
        val runningInPlace = Exercise(getString(R.string.running_in_place), 30, 1)
        val lunges = Exercise(getString(R.string.lunges), 30, 10)
        val pushUpWithRotationRight = Exercise(
            getString(R.string.push_up_with_rotation_right),
            30,
            5
        )
        val pushUpwithRotationLeft = Exercise(
            getString(R.string.push_up_with_rotation_left),
            30,
            5
        )
        val sidePlankRight = Exercise(
            getString(R.string.side_plank_right),
            30,
            1
        )
        val sidePlankLeft = Exercise(
            getString(R.string.side_plank_left),
            30,
            1
        )

        val sevenMinuteWorkout = Workout(
            getString(R.string.seven_minute),
            R.drawable.sevenminutelogo,
            listOf(
                jumpingJacks,
                wallSit,
                pushUp,
                crunches,
                stepUps,
                squats,
                tricepDips,
                plank,
                runningInPlace,
                lunges,
                pushUpWithRotationRight,
                pushUpwithRotationLeft,
                sidePlankRight,
                sidePlankLeft
            )
        )

        val absWorkout = Workout(
            getString(R.string.abs_workout),
            R.drawable.blue_creature_abs_workout,
            listOf(
                crunches
            )
        )
        return listOf(sevenMinuteWorkout, absWorkout)
    }
}
