package dev.shinyparadise.wordcraft.model.state

interface AttemptBasedState {
    val maxAttempts: Int
    val currentAttempt: Int
}
