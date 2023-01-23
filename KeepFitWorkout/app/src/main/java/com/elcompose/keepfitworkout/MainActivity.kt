package com.elcompose.keepfitworkout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elcompose.keepfitworkout.screens.ExerciseScreen
import com.elcompose.keepfitworkout.screens.HomeScreen
import com.elcompose.keepfitworkout.ui.theme.KeepFitWorkoutTheme
import com.elcompose.keepfitworkout.util.Workout
import com.elcompose.keepfitworkout.util.WorkoutState

import com.elcompose.keepfitworkout.util.getWorkouts

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val workouts = getWorkouts(this)
        var currentWorkout = Workout("",R.drawable.furry_blue_creature_with_ear, listOf())
        setContent {
            KeepFitWorkoutTheme {
                val exerciseModel: ExerciseModel = viewModel()
                var exercising by rememberSaveable {
                    mutableStateOf(false)
                }
                val onStartWorkout = { workout: Workout ->
                    currentWorkout = workout
                    exercising = true
                    exerciseModel.setCurrentWorkout(workout)
                }
                val onExerciseOver = {
                    exercising = false
                }
                val videoPlay = {
                    exerciseModel.playAndPauseWorkOut()
                }
                val currentWorkoutState =  exerciseModel.workoutState.observeAsState()
                val remainingTime = exerciseModel.remainingTime.observeAsState()
                val exerciseName = exerciseModel.currentExerciseName.observeAsState()

                if (exercising) {
                    ExerciseScreen(
                        exerciseName = exerciseName.value ?: "Workout Error",
                        timeRemains = remainingTime.value ?: 0,
                        playButtonListener = videoPlay,
                        totalDuration = exerciseModel.getTotalWorkoutDuration(),
                        workoutState = currentWorkoutState.value ?: WorkoutState.STOPPED,
                        finishButtonListener = {
                            exercising = false
                            exerciseModel.resetWorkout()
                        },
                        backHandler = {
                            exercising = false
                            exerciseModel.resetWorkout()
                        }
                    )
                } else {
                    HomeScreen(workouts = workouts, onStartWorkout = onStartWorkout)
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