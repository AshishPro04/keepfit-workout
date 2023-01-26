package com.elcompose.keepfitworkout.screens

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.lifecycle.LifecycleOwner
import com.elcompose.keepfitworkout.R
import com.elcompose.keepfitworkout.ui.theme.KeepFitWorkoutTheme
import com.elcompose.keepfitworkout.util.WorkoutState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun ExerciseTitle(modifier: Modifier = Modifier, exercise: String) {
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
    workoutState: WorkoutState,
) {
    val progress by animateFloatAsState(targetValue = timeLeft.toFloat() / totalDuration.toFloat())
    Surface(
        modifier = modifier,
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column() {
            ProgressIndicator(progress)

            FullWidthSurfacePrimary(
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 2.dp),
                shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
            ) {
                Column() {

                    Row(
                        modifier = Modifier
                            .padding(all = 8.dp)
                            .fillMaxWidth(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        RemainingTime(timeLeft)

                        Column(
                            modifier = Modifier.padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            FinishButton(onFinishWorkout)
                            PlayButton(onPlayPause, workoutState)
                        }
                    }
                }
            }
            FullWidthSurfacePrimary(
                modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp, top = 2.dp),
                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
            ) {
                TotalTime(totalTime = totalDuration)
            }
        }
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
fun AllExercises(exercises: List<String>, currentExerciseName: String) {
    Surface (
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
        shadowElevation = 2.dp,
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(4.dp)
    ){
        val scrollState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 8.dp),
            state = scrollState
        ) {
            itemsIndexed(exercises){ index, exercise ->
                SingleExerciseInList(
                    exercise = exercise,
                    currentExercise = currentExerciseName,
                    onExerciseChanged = {
                        scope.launch {
                            scrollState.animateScrollToItem(index, scrollOffset = -2)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun SingleExerciseInList(exercise: String, currentExercise: String, onExerciseChanged: () -> Unit) {
    val color = if (currentExercise == exercise) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.secondaryContainer
    }
    Surface(
        modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 4.dp)
            .width(IntrinsicSize.Max),
        shape = RoundedCornerShape(5.dp),
        color = color
    ) {
        Column() {
            Text(modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp), text = exercise)
            AnimatedVisibility (currentExercise == exercise) {
                Spacer(
                    modifier = Modifier
                        .height(4.dp)
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.primary)
                )
                onExerciseChanged()
            }
        }
    }
}

@Composable
private fun TotalTime(totalTime: Int) {
    Text(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .wrapContentWidth(),
        text = "Total duration: $totalTime",
        style = MaterialTheme.typography.headlineSmall
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
            .width(200.dp),
        onClick = onPlayPause
    ) {
        PlayButtonContents(workoutState = workoutState)
    }
}

@Composable
private fun FinishButton(onFinishWorkout: () -> Unit) {
    Button(
        modifier = Modifier
            .padding(all = 8.dp)
            .width(200.dp),
        onClick = onFinishWorkout
    ) {
        Text(
            modifier = Modifier
                .padding(all = 8.dp)
                .width(200.dp)
                .fillMaxWidth(1f)
                .wrapContentWidth(),
            text = stringResource(id = R.string.finish_workout),
            style = MaterialTheme.typography.titleSmall
        )
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
        fontSize = 48.sp,
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
    exerciseName: String,
    timeRemains: Int,
    totalDuration: Int,
    playButtonListener: () -> Unit,
    finishButtonListener: () -> Unit,
    workoutState: WorkoutState,
    backHandler: () -> Unit,
    exerciseNameList: List<String>,
    currentExerciseName: String
) {
    BackHandler {
        backHandler()
    }
    Surface(modifier = Modifier.fillMaxSize(1f)) {
        Column() {
            ExerciseTitle(modifier = Modifier.fillMaxWidth(1f), exercise = exerciseName)
            ExerciseTimer(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                timeLeft = timeRemains,
                totalDuration = totalDuration,
                onPlayPause = playButtonListener,
                workoutState = workoutState,
                onFinishWorkout = finishButtonListener
            )
            AllExercises(exercises = exerciseNameList, currentExerciseName = currentExerciseName)
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
            onFinishWorkout = {}
        )
    }
}