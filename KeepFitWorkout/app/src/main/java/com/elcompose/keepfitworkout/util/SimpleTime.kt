package com.elcompose.keepfitworkout.util

class SimpleTime(var hour: Int, var min: Int, var sec: Int ) {
    init {
        require(hour in 0..24){ "That's invalid hour value" }
        require(min in 0..60){ "This is invalid min value" }
        require(sec in 0..60){ "This is invalid sec value" }
    }

    companion object {
        fun getTimeFromSeconds(seconds: Int): SimpleTime {
            val sec = seconds % 60
            val minutes = seconds / 60
            val min = minutes % 60
            val hour = minutes / 60
            return SimpleTime(hour, min, sec)
        }
    }
    override fun toString(): String {
        return if (hour != 0) "$hour: $min: $sec" else "$min: $sec"
    }
}