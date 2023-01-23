package com.elcompose.keepfitworkout.screens
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elcompose.keepfitworkout.R
import com.elcompose.keepfitworkout.ui.theme.KeepFitWorkoutTheme
import com.elcompose.keepfitworkout.util.Workout

@Composable
fun AppTitle(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shadowElevation = 4.dp,
        tonalElevation = 1.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.headlineLarge
            )
            Image(
                painter = painterResource(id = R.drawable.blue_creatures_running),
                contentDescription = null
            )
        }
    }
}

@Composable
fun Workout(modifier: Modifier = Modifier, workout: Workout, onStartWorkout: (Workout) -> Unit) {
    Surface(
        modifier = modifier,
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(4.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(0.33f)
                    .padding(all = 8.dp)
                    .clip(RoundedCornerShape(6.dp)),
                painter = painterResource(id = workout.id),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth(1f)
                    .wrapContentWidth()
            ) {
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = stringResource(
                        id = R.string.duration_label,
                        workout.getDuration().toString()
                    ),
                    style = MaterialTheme.typography.headlineSmall
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .wrapContentWidth(align = Alignment.End)
                        .padding(all = 16.dp),
                    onClick = {onStartWorkout(workout)}
                ) {
                    Text(text = stringResource(id = R.string.start_workout))
                }
            }

        }
    }
}

@Composable
fun SectionHeader(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.headlineMedium.copy(
            shadow = Shadow(
                color = MaterialTheme.colorScheme.primaryContainer,
                offset = Offset(0.3f, 0.2f),
                blurRadius = 0.1f
            )
        )
    )
}

@Composable
fun WorkoutList(
    modifier: Modifier = Modifier,
    workoutList: List<Workout>,
    onStartWorkout: (Workout) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 4.dp, vertical = 8.dp)
    ){
        items(workoutList) {workout ->
            Workout(
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 4.dp),
                workout = workout,
                onStartWorkout = onStartWorkout
            )

        }
    }
}

@Composable
fun HomeScreen(workouts: List<Workout>, onStartWorkout: (Workout) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(1f),
        shadowElevation = 2.dp,
        color = MaterialTheme.colorScheme.background
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.scrollable(scrollState, orientation = Orientation.Vertical)
        ) {
            val headerModifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp)
            AppTitle(modifier = Modifier.fillMaxWidth(1f))
            SectionHeader(
                modifier = headerModifier,
                text = stringResource(id = R.string.workouts_heading)
            )
            WorkoutList(workoutList = workouts, onStartWorkout = onStartWorkout)
        }
    }
}

@Preview(name = "Home Night", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Home Day", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewHomePage(){
    KeepFitWorkoutTheme {
        HomeScreen(listOf()){

        }
    }
}