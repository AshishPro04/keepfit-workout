package com.elcompose.keepfitworkout.util

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.ui.res.stringResource
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
        val tricepsDips = Exercise(getString(R.string.tricep_dips), 30, 10)
        val plank = Exercise(getString(R.string.plank), 30, 1)
        val runningInPlace = Exercise(getString(R.string.running_in_place), 30, 1)
        val lunges = Exercise(getString(R.string.lunges), 30, 10)
        val reverseCrunches = Exercise(getString(R.string.reverse_crunches), 30, 10)
        val testExercise = Exercise("Test Exercise",5, 2)
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

        val legRaises = Exercise(
            getString(R.string.leg_raises),
            30,
            10
        )

        val squatToObliqueCrunches = Exercise(
            getString(R.string.squat_to_oblique_crunches),
            30,
            10
        )

        val calfRaises = Exercise(
            getString(R.string.calf_raises),
            30,
            10
        )

        val diamondPushUps = Exercise(
            getString(R.string.diamond_push_ups),
            30,
            10
        )

        val declinePushups = Exercise(
            getString(R.string.decline_push_ups),
            30,
            10
        )

        val plankToPushUps = Exercise(
            getString(R.string.plank_to_push_up),
            30,
            10
        )

        val isometricHolds = Exercise(
            getString(R.string.isometric_holds),
            30,
            10
        )

        val dips = Exercise(
            getString(R.string.dips),
            30,
            10
        )

        val inclinePushUps= Exercise(
            getString(R.string.incline_push_ups),
            30,
            10
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
                tricepsDips,
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
                crunches,
                plank,
                legRaises,
                reverseCrunches
            )
        )
        val legsWorkout = Workout(
            getString(R.string.legs_workout),
            R.drawable.leg_workout,
            listOf(
                squats,
                lunges,
                stepUps,
                squatToObliqueCrunches,
                calfRaises
            )
        )

        val armsWorkout = Workout(
            getString(R.string.arms_workout),
            R.drawable.cute_blue_arms_workout,
            listOf(
                pushUp,
                diamondPushUps,
                tricepsDips,
                plankToPushUps,
                isometricHolds
            )
        )

        val chestWorkout = Workout(
            getString(R.string.chest_workout),
            R.drawable.chest_workout,
            listOf(
                pushUp,
                declinePushups,
                dips,
                inclinePushUps,
                isometricHolds
            )
        )
        val testWorkout = Workout(
            "TEST",
            R.drawable.chest_workout,
            listOf(testExercise)
        )
        return listOf(
            sevenMinuteWorkout,
            absWorkout,
            chestWorkout,
            legsWorkout,
            armsWorkout
        )
    }
}
