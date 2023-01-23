package com.elcompose.keepfitworkout.screens

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elcompose.keepfitworkout.R
import com.elcompose.keepfitworkout.ui.theme.KeepFitWorkoutTheme
import com.elcompose.keepfitworkout.util.WorkoutState

@Composable
fun ExerciseTitle(modifier: Modifier = Modifier, exercise: String) {
    Surface(modifier = modifier) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
            text = exercise,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun ExerciseTimer(
    modifier: Modifier = Modifier,
    timeLeft: Int,
    totalDuration: Int,
    onPlayPause: () -> Unit,
    onFinishWorkout: () -> Unit,
    workoutState: WorkoutState
) {
    val progress by animateFloatAsState(targetValue = timeLeft.toFloat() / totalDuration.toFloat())

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 2.dp,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {
        Column() {
            Spacer(modifier = Modifier
                .fillMaxWidth(progress)
                .height(4.dp)
                .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
                .padding(horizontal = 8.dp)
            )
            Row(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    text = timeLeft.toString(),
                    style = MaterialTheme.typography.headlineMedium
                )
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        modifier = Modifier.padding(all = 8.dp).width(200.dp),
                        onClick = onFinishWorkout
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(all = 8.dp).width(200.dp).
                                fillMaxWidth(1f).
                                wrapContentWidth(),
                            text = stringResource(id = R.string.finish_workout),
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                    Button(
                        modifier = Modifier.padding(all = 8.dp).width(200.dp),
                        onClick = onPlayPause
                    ) {
                        PlayButtonContents(workoutState = workoutState)
                    }
                }
            }
        }
    }
}

@Composable
fun PlayButtonContents(workoutState: WorkoutState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        val btnTxtStyle = MaterialTheme.typography.titleSmall
        val btnTxtModifier = Modifier.padding(all = 8.dp)
        when (workoutState) {
            WorkoutState.STARTED -> {
                Icon(
                    painter = painterResource(id = R.drawable.pause_icon),
                    contentDescription = null
                )
                Text(
                    modifier = btnTxtModifier,
                    text = stringResource(id = R.string.pause_workout),
                    style = btnTxtStyle,
                    textAlign = TextAlign.Center
                )
            }
            WorkoutState.PAUSED -> {
                Icon(
                    painter = painterResource(id = R.drawable.play_icon),
                    contentDescription = null
                )
                Text(
                    modifier = btnTxtModifier,
                    text = stringResource(id = R.string.resume_workout),
                    style = btnTxtStyle,
                    textAlign = TextAlign.Center
                )
            }
            WorkoutState.STOPPED -> {
                Icon(
                    painter = painterResource(id = R.drawable.play_icon),
                    contentDescription = null
                )
                Text(
                    modifier = btnTxtModifier,
                    text = stringResource(id = R.string.start_workout),
                    style = btnTxtStyle,
                    textAlign = TextAlign.Center
                )
            }
        }
        
    }
}

@Composable
fun ExerciseScreen(
    exerciseName: String,
    timeRemains: Int,
    totalDuration: Int,
    playButtonListener: () -> Unit,
    finishButtonListener: () -> Unit,
    workoutState: WorkoutState,
    backHandler: () -> Unit
) {
    BackHandler {
        backHandler()
    }
    Surface(modifier = Modifier.fillMaxSize(1f)) {
        Column() {
            ExerciseTitle(exercise = exerciseName)
            ExerciseTimer(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                timeLeft = timeRemains,
                totalDuration = totalDuration,
                onPlayPause = playButtonListener,
                workoutState = workoutState,
                onFinishWorkout = finishButtonListener
            )
        }
    }
}

@Preview(name = "Timer Preview Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name =  "Timer Preview Day", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewExerciseTimer(){
    KeepFitWorkoutTheme() {
        ExerciseTimer(
            modifier = Modifier.fillMaxWidth(1f),
            timeLeft = 8,
            totalDuration = 8,
            onPlayPause = { /*TODO*/ },
            workoutState = WorkoutState.PAUSED,
            onFinishWorkout = {}
        )
    }
}