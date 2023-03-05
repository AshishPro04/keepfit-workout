package com.elcompose.keepfitworkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elcompose.keepfitworkout.util.Exercise
import com.elcompose.keepfitworkout.util.SimpleTime
import com.elcompose.keepfitworkout.util.Workout
import com.elcompose.keepfitworkout.util.WorkoutState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExerciseModel : ViewModel() {
    private val lock = Any()
    private var exerciseDuration: Int = 0

    private val _remainingTime: MutableLiveData<Int> = MutableLiveData()
    val remainingTime: LiveData<Int> = _remainingTime

    private val _workoutState: MutableLiveData<WorkoutState> = MutableLiveData()
    val workoutState: LiveData<WorkoutState> = _workoutState

    private val _currentExerciseName: MutableLiveData<String> = MutableLiveData()
    val currentExerciseName: LiveData<String> = _currentExerciseName

    private val _nextExerciseName: MutableLiveData<String> = MutableLiveData()
    val nextExerciseName: LiveData<String> = _nextExerciseName

    private val _exercisedTime: MutableLiveData<Int> = MutableLiveData(0)
    val exercisedTime:LiveData<Int> = _exercisedTime

    private var totalTimeExercised: Int = 0


    private var totalWorkoutTime: Int = 0
    private var timeExercised = 0

    private var currentExercise: Exercise? = null

    private var currentWorkout: Workout = Workout("Dummy", R.drawable.play_icon, listOf())
    private var currentIndex: Int = 0

    fun getTotalExerciseDuration(): Int {
        return exerciseDuration
    }

    private fun setDuration(duration: Int) {
        exerciseDuration = duration
        _remainingTime.value = duration
    }

    fun changeCurrentWorkout(workout: Workout) {
        timeExercised = 0
        totalTimeExercised = 0
        currentWorkout = workout
        setCurrentExercise(currentWorkout.exercises.firstOrNull())
        currentIndex = 0
        totalWorkoutTime = currentWorkout.getDuration().getSeconds()
    }
    fun readCurrentWorkout(): Workout {
        return currentWorkout
    }

    private fun setCurrentExercise(exercise: Exercise?) {
        currentExercise = exercise
        setDuration(exercise?.duration ?: 0)
        _currentExerciseName.value = currentExercise?.name
    }


    private fun startTimer() {
        synchronized(lock) {
            viewModelScope.launch {
                _workoutState.value = WorkoutState.STARTED
                while (
                    workoutState.value != WorkoutState.STOPPED &&
                    workoutState.value != WorkoutState.PAUSED &&
                    workoutState.value != WorkoutState.FINISHED
                ) {
                    while (
                        workoutState.value == WorkoutState.STARTED &&
                        (_remainingTime.value ?: 0) > 0
                    ) {
                        delay(1000)
                        _remainingTime.value = ((_remainingTime.value ?: 1) - 1)
                        timeExercised++
                        _exercisedTime.value = totalTimeExercised + timeExercised
                        if (
                            (_remainingTime.value ?: 0) == 0 &&
                            workoutState.value == WorkoutState.STARTED
                        ) {
                            _workoutState.value = WorkoutState.ENDED
                        }
                    }
                    if (workoutState.value == WorkoutState.STOPPED) {
                        stopWorkout()
                        timeExercised = 0
                    } else if (workoutState.value == WorkoutState.ENDED) {
                        ++currentIndex
                        val nextExercise = currentWorkout.exercises.getOrNull(currentIndex)
                        if (nextExercise == null) {
                            //TODO: Stop and Finish Workout here
                            stopWorkout()
                            _workoutState.value = WorkoutState.FINISHED
                        } else {
                            totalTimeExercised += timeExercised
                            setCurrentExercise(nextExercise)
                            _workoutState.value = WorkoutState.STARTED
                        }
                        timeExercised = 0
                    }
                }
            }
        }
    }

    fun playAndPauseWorkOut() {
        if (_workoutState.value == WorkoutState.STARTED) {
            pauseWorkout()
        } else {
            startTimer()
        }
    }

    private fun pauseWorkout() {
        _workoutState.postValue(WorkoutState.PAUSED)
    }

    private fun stopWorkout() {
        _workoutState.value = WorkoutState.STOPPED
        _remainingTime.value = exerciseDuration
        _exercisedTime.value = 0
    }

    fun restartExercise() {
        _remainingTime.value = currentExercise?.duration ?: 0
    }

    fun resetWorkout() {
        stopWorkout()
        currentIndex = 0
    }
}
