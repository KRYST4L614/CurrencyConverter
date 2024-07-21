package com.example.currencyconverter.shared.animations

import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce

fun bounce(view: View, finalPosition: Float, startVelocity: Float) {
    val springForce = SpringForce(finalPosition)
        .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
        .setStiffness(SpringForce.STIFFNESS_LOW)
    SpringAnimation(view, DynamicAnimation.TRANSLATION_X).apply {
        setStartVelocity(startVelocity)
        spring = springForce
        start()
    }
}