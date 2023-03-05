package com.elcompose.keepfitworkout.screens.exercisescreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.elcompose.keepfitworkout.util.WorkoutState
import kotlinx.coroutines.launch


@Composable
fun AllExercises(exercises: List<String>, currentExerciseName: String, workoutState: WorkoutState) {
    AnimatedVisibility(visible = workoutState != WorkoutState.FINISHED) {
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