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
    fun getSeconds(): Int {
        return (hour * 3600) + (min * 60) + sec
    }
    override fun toString(): String {
        return if (hour != 0){
            String.format("%1$02d: %2$02d: %3$02d", hour, min, sec )
        } else {
            String.format("%1$02d: %2$02d", min, sec )
        }
    }
}