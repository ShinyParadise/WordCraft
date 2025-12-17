package dev.shinyparadise.wordcraft.model.state

object WordleEvaluator {

    fun evaluateGuess(
        guess: String,
        target: String
    ): List<LetterResult> {

        val result = MutableList(guess.length) { LetterResult.ABSENT }
        val targetChars = target.toMutableList()

        // 1. Точные совпадения
        for (i in guess.indices) {
            if (guess[i] == target[i]) {
                result[i] = LetterResult.CORRECT
                targetChars[i] = '*'
            }
        }

        // 2. Буквы, которые есть в слове
        for (i in guess.indices) {
            if (result[i] == LetterResult.ABSENT &&
                targetChars.contains(guess[i])
            ) {
                result[i] = LetterResult.PRESENT
                targetChars[targetChars.indexOf(guess[i])] = '*'
            }
        }

        return result
    }
}
