package com.example.teacherassistant.Logics

fun gapSentenceMaker(input: String): List<String> {
    //TODO the contractions also have to be gapped!
    /*val expanded = input
        .replace("(?i)(\\b)([A-Za-z])'([A-Za-z]+)".toRegex()) { "${it.groupValues[1]}${it.groupValues[2]} ${it.groupValues[3]}" }

    val words = expanded.split(" ")*/
    val words = input.replace("\n", "*\n").split(" ","\n")

    return words.indices.map { index ->
        words.mapIndexed { i, word ->
            if (i == index) {
                val prefix = word.takeWhile { !it.isLetterOrDigit() }
                val core =
                    word.dropWhile { !it.isLetterOrDigit() }.takeWhile { it.isLetterOrDigit() }
                val suffix = word.drop(prefix.length + core.length)
                prefix + "_".repeat(core.length) + suffix
            } else {
                word
            }
        }.joinToString(" ").replace("*", "\n")
    }
}