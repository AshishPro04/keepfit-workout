package com.elcompose.keepfitworkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elcompose.keepfitworkout.util.Exercise
import com.elcompose.keepfitworkout.util.Workout
import com.elcompose.keepfitworkout.util.WorkoutState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ExerciseModel : ViewModel() {
    private val lock = Any()
    private var workoutDuration: Int = 0

    private val _remainingTime: MutableLiveData<Int> = MutableLiveData()
    val remainingTime: LiveData<Int> = _remainingTime

    private val _workoutState: MutableLiveData<WorkoutState> = MutableLiveData()
    val workoutState: LiveData<WorkoutState> = _workoutState

    private val _currentExerciseName: MutableLiveData<String> = MutableLiveData()
    val currentExerciseName: LiveData<String> = _currentExerciseName

    private val _nextExerciseName: MutableLiveData<String> = MutableLiveData()
    val nextExerciseName: LiveData<String> = _nextExerciseName

    private val _exercisedTime: MutableLiveData<String> = MutableLiveData("0s")
    val exercisedTime:LiveData<String> = _exercisedTime

    private var totalWorkoutTime: String = "0:0 s"


    private var currentExercise: Exercise? = null
    private var currentWorkout: Workout = Workout("Dummy", R.drawable.play_icon, listOf())

    private var currentIndex: Int = 0

    fun getTotalWorkoutDuration(): Int {
        return workoutDuration
    }

    private fun setDuration(duration: Int) {
        workoutDuration = duration
        _remainingTime.value = duration
    }

    fun changeCurrentWorkout(workout: Workout) {
        currentWorkout = workout
        setCurrentExercise(currentWorkout.exercises.firstOrNull())
        currentIndex = 0
        totalWorkoutTime = currentWorkout.getDuration().toString()
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
                while (workoutState.value != WorkoutState.STOPPED && workoutState.value != WorkoutState.PAUSED) {
                    while (
                        workoutState.value == WorkoutState.STARTED &&
                        (_remainingTime.value ?: 0) > 0
                    ) {
                        _remainingTime.value = ((_remainingTime.value ?: 1) - 1)
                        delay(1000)
                        if ((_remainingTime.value ?: 0) == 0 && workoutState.value == WorkoutState.STARTED) {
                            _workoutState.value = WorkoutState.ENDED
                        }
                    }
                    if (workoutState.value == WorkoutState.STOPPED) {
                        stopWorkout()
                    } else if (workoutState.value == WorkoutState.ENDED) {
                        ++currentIndex
                        val nextExercise = currentWorkout.exercises.getOrNull(currentIndex)
                        if (nextExercise == null) {
                            stopWorkout()
                        } else {
                            setCurrentExercise(nextExercise)
                            _workoutState.value = WorkoutState.STARTED
                        }
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
        _remainingTime.value = workoutDuration
    }

    fun restartExercise() {
        _remainingTime.value = currentExercise?.duration ?: 0
    }

    fun resetWorkout() {
        stopWorkout()
        currentIndex = 0
    }
}
