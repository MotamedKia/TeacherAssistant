package com.example.teacherassistant.Logics

fun scrambledWord(input: String): String {
    val regex = Regex("""\w+""")
    return input.replace(regex) { matchResult ->
        val word = matchResult.value
        if (word.length > 1) {
            word.toList().shuffled().joinToString("")
        } else {
            word
        }
    }
}