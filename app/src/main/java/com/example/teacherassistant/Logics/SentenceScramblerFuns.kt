package com.example.teacherassistant.Logics

import kotlin.random.Random

fun scrambledSentence(input: String): String {
    val words = input.split(" ").shuffled(Random.Default)
    return words.joinToString("/")
}