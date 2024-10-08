package com.elcompose.keepfitworkout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elcompose.keepfitworkout.screens.exercisescreen.ExerciseScreen
import com.elcompose.keepfitworkout.screens.HomeScreen
import com.elcompose.keepfitworkout.ui.theme.KeepFitWorkoutTheme
import com.elcompose.keepfitworkout.util.Workout
import com.elcompose.keepfitworkout.util.WorkoutState
import com.elcompose.keepfitworkout.util.getExerciseNames

import com.elcompose.keepfitworkout.util.getWorkouts

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val workouts = getWorkouts(this)
        var currentWorkout = Workout("", R.drawable.furry_blue_creature_with_ear, listOf())
        setContent {
            KeepFitWorkoutTheme {
                val navController = rememberNavController()
                val exerciseModel: ExerciseModel = viewModel()
                var exercising by rememberSaveable {
                    mutableStateOf(false)
                }
                val onStartWorkout = { workout: Workout ->
                    currentWorkout = workout
                    exercising = true
                    exerciseModel.changeCurrentWorkout(workout)
                    navController.navigate(ExerciseScreen)
                }
                val onRestartExercise = {
                    exerciseModel.restartExercise()
                }
                val onExerciseOver = {
                    exercising = false
                }
                val videoPlay = {
                    exerciseModel.playAndPauseWorkOut()
                }
                val currentWorkoutState = exerciseModel.workoutState.collectAsState()
                val remainingTime = exerciseModel.remainingTime.collectAsState()
                val exerciseName = exerciseModel.currentExerciseName.collectAsState()
                val workedOutTime = exerciseModel.exercisedTime.collectAsState()


                NavHost(navController = navController, startDestination = HomeScreen) {
                    composable<HomeScreen>() {
                        HomeScreen(workouts = workouts, onStartWorkout = onStartWorkout)
                    }
                    composable<ExerciseScreen>() {
                        if (exercising) {
                            ExerciseScreen(
                                workoutName = exerciseModel.readCurrentWorkout().name,
                                exerciseName = exerciseName.value ?: "Workout Error",
                                timeRemains = remainingTime.value ?: 0,
                                playButtonListener = videoPlay,
                                exerciseDuration = exerciseModel.getTotalExerciseDuration(),
                                workoutState = currentWorkoutState.value ?: WorkoutState.STOPPED,
                                finishButtonListener = {
                                    exercising = false
                                    exerciseModel.resetWorkout()
                                },
                                restartButtonListener = onRestartExercise,
                                backHandler = {
                                    exercising = false
                                    exerciseModel.resetWorkout()
                                },
                                exerciseNameList = exerciseModel.readCurrentWorkout()
                                    .getExerciseNames(),
                                currentExerciseName = exerciseName.value,
                                workedOutTime = workedOutTime.value,
                                totalWorkoutTime = exerciseModel
                                    .readCurrentWorkout()
                                    .getDuration()
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    KeepFitWorkoutTheme {
        Greeting("Android")
    }
}