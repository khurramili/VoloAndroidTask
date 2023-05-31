package com.volo.voloandroidtask.constants

object Constants {

    const val thresholdX = 200
    const val thresholdY = 100
    const val thresholdZ = 100

    const val collisionAvoidDistance = 200
    const val wifiPassword ="volodrone"

    fun convertZtoAngleDegree(z:Float): Double {
        return Math.toDegrees(z.toDouble())
    }
}