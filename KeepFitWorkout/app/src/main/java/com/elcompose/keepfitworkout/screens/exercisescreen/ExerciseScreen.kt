package com.elcompose.keepfitworkout.screens.exercisescreen

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elcompose.keepfitworkout.R
import com.elcompose.keepfitworkout.ui.theme.KeepFitWorkoutTheme
import com.elcompose.keepfitworkout.util.SimpleTime
import com.elcompose.keepfitworkout.util.Workout
import com.elcompose.keepfitworkout.util.WorkoutState

@Composable
fun WorkoutTitle(modifier: Modifier = Modifier, exercise: String) {
    Surface(modifier = modifier, tonalElevation = 2.dp) {
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
    onRestartExercise: () -> Unit,
    workoutState: WorkoutState,
    exerciseName: String
) {
    val progress by animateFloatAsState(targetValue = timeLeft.toFloat() / totalDuration.toFloat())
    Surface(
        modifier = modifier,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        Column() {
            ProgressIndicator(progress)
            ExerciseName(exerciseName = exerciseName)
            FullWidthSurfacePrimary(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth(1f)
                        .height(IntrinsicSize.Max),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(IntrinsicSize.Max)
                            .fillMaxHeight(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        RemainingTime(timeLeft)
                        Divider()
                        ExerciseTime(totalTime = totalDuration)
                    }
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .width(IntrinsicSize.Max),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PlayButton(onPlayPause, workoutState)
                        FinishButton(onFinishWorkout)
                        RestartButton(onRestartExercise = onRestartExercise)
                    }
                }
            }
        }
    }
}

@Composable
fun ExerciseName(modifier: Modifier = Modifier, exerciseName: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth(1f)
            .wrapContentWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        text = exerciseName,
        style = MaterialTheme.typography.headlineMedium
    )
    FullWidthSurfacePrimary(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
    }
}

@Composable
fun FullWidthSurfacePrimary(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(8.dp),
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = shape,
        shadowElevation = 2.dp,
        color = color,
        content = content
    )
}

@Composable
fun NextExercise(workoutName: String, duration: Int) {
    Surface() {
    }
}


@Composable
private fun ExerciseTime(totalTime: Int) {
    Text(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .wrapContentWidth(),
        text = stringResource(id = R.string.exercise_duration_label, totalTime),
    )
}

@Composable
private fun PlayButton(
    onPlayPause: () -> Unit,
    workoutState: WorkoutState
) {
    Button(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
        onClick = onPlayPause,
        elevation = ButtonDefaults.elevatedButtonElevation()
    ) {
        PlayButtonContents(workoutState = workoutState)
    }
}

@Composable
private fun FinishButton(onFinishWorkout: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(all = 8.dp)
            .fillMaxWidth(),
        onClick = onFinishWorkout
    ) {
        Text(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth(1f)
                .wrapContentWidth(),
            text = stringResource(id = R.string.finish_workout),
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
private fun RestartButton(modifier: Modifier = Modifier, onRestartExercise: () -> Unit) {
    OutlinedButton(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .fillMaxWidth(),
        onClick = onRestartExercise) {
        Text(text = stringResource(id = R.string.restart_exercise))
    }
}

@Composable
private fun RemainingTime(timeLeft: Int) {
    Text(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .wrapContentHeight(),
        text = timeLeft.toString(),
        style = MaterialTheme.typography.headlineLarge,
        fontSize = 64.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
private fun ProgressIndicator(progress: Float) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth(progress)
            .height(4.dp)
            .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
            .padding(horizontal = 8.dp)
    )
}

@Composable
fun PlayButtonContents(workoutState: WorkoutState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        when (workoutState) {
            WorkoutState.STARTED -> {
                PlayButtonContent(iconId = R.drawable.pause_icon, stringId = R.string.pause_workout)
            }
            WorkoutState.PAUSED -> {
                PlayButtonContent(iconId = R.drawable.play_icon, stringId = R.string.resume_workout)
            }
            WorkoutState.STOPPED -> {
                PlayButtonContent(iconId = R.drawable.play_icon, stringId = R.string.start_workout)
            }
        }

    }
}

@Composable
fun PlayButtonContent(@DrawableRes iconId: Int, @StringRes stringId: Int) {
    val btnTxtStyle = MaterialTheme.typography.titleSmall
    val btnTxtModifier = Modifier.padding(all = 8.dp)
    Icon(
        painter = painterResource(id = iconId),
        contentDescription = null
    )
    Text(
        modifier = btnTxtModifier,
        text = stringResource(id = stringId),
        style = btnTxtStyle,
        textAlign = TextAlign.Center
    )
}

@Composable
fun ExerciseScreen(
    workoutName: String,
    exerciseName: String,
    timeRemains: Int,
    exerciseDuration: Int,
    playButtonListener: () -> Unit,
    finishButtonListener: () -> Unit,
    restartButtonListener: () -> Unit,
    workoutState: WorkoutState,
    backHandler: () -> Unit,
    exerciseNameList: List<String>,
    currentExerciseName: String,
    workedOutTime: Int,
    totalWorkoutTime: SimpleTime
) {
    BackHandler {
        backHandler()
    }
    Surface(modifier = Modifier.fillMaxSize(1f)) {
        Column() {
            WorkoutTitle(modifier = Modifier.fillMaxWidth(1f), exercise = workoutName)
            WorkoutTime(
                workedOutTime = SimpleTime.getTimeFromSeconds(workedOutTime),
                totalTime = totalWorkoutTime
            )
            AllExercises(exercises = exerciseNameList, currentExerciseName = currentExerciseName)
            ExerciseTimer(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                timeLeft = timeRemains,
                totalDuration = exerciseDuration,
                onPlayPause = playButtonListener,
                workoutState = workoutState,
                onFinishWorkout = finishButtonListener,
                onRestartExercise = restartButtonListener,
                exerciseName = exerciseName
            )
        }
    }
}

@Composable
fun WorkoutTime(modifier: Modifier = Modifier, workedOutTime: SimpleTime, totalTime: SimpleTime) {
    FullWidthSurfacePrimary(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(2.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        val maxTimeSeconds = totalTime.getSeconds()
        val workedOutSeconds = workedOutTime.getSeconds()
        val progress by animateFloatAsState(
            targetValue = workedOutSeconds.toFloat() / maxTimeSeconds
        )
        Box(modifier = Modifier.height(IntrinsicSize.Max)) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .fillMaxHeight(),
                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.25f)
            ) {

            }
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                val txtModifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
                Text(modifier = txtModifier, text = workedOutTime.toString())
                Text(modifier= txtModifier, text = totalTime.toString())
            }
        }
    }
}

@Preview(name = "Next Workout")
@Composable
fun NextWorkoutPreview() {
    KeepFitWorkoutTheme() {
        NextExercise(workoutName = "Pull Up", duration = 30)
    }
}

@Preview(name = "Timer Preview Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Timer Preview Day", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewExerciseTimer() {
    KeepFitWorkoutTheme() {
        ExerciseTimer(
            modifier = Modifier.fillMaxWidth(1f),
            timeLeft = 8,
            totalDuration = 8,
            onPlayPause = { /*TODO*/ },
            workoutState = WorkoutState.PAUSED,
            onFinishWorkout = {},
            onRestartExercise = {},
            exerciseName = "Jump"
        )
    }
}

@Preview
@Composable
fun PreviewExerciseScreen() {
    KeepFitWorkoutTheme() {
        ExerciseScreen(
            workoutName = "Running 7",
            exerciseName = "Spriunt 100",
            timeRemains = 20,
            exerciseDuration = 450,
            playButtonListener = { /*TODO*/ },
            finishButtonListener = { /*TODO*/ },
            restartButtonListener = { /*TODO*/ },
            workoutState = WorkoutState.STARTED,
            backHandler = { /*TODO*/ },
            exerciseNameList = listOf("Running 100", "Running 200", "Running 300"),
            currentExerciseName = "Running 100",
            workedOutTime = 55,
            totalWorkoutTime = SimpleTime(0,7,0)
        )

    }
}