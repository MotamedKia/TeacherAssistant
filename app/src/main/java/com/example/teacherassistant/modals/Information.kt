package com.example.teacherassistant.modals

data class Information(val id: Int, val name: String, val description: String)

val informList = listOf<Information>(
    Information(
        1,
        "Word Scrambler",
        "This function takes a sentence and mixes up the letters inside each word that has more than one letter. " +
                "The new version of each word has the same letters, just in a different order.\n" +
                "Example: \"I love teaching.\"\n" +
                "\"I evol hnaicetg.\""
    ),
    Information(
        2,
        "Sentence Scrambler",
        "This function takes a sentence and mixes up the order of the words. Then it joins the words back together using slashes (/) instead of spaces. " +
                "The original words stay the same, only their order changes.\n" +
                "Example: \"I love teaching.\"\n" +
                "\"teaching./I/love\""
    ),
    Information(
        3,
        "Fill Gaps",
        "This function creates a list of sentences, each with one word replaced by blanks. The blanks are the same length as the missing word. " +
                "It helps show gaps one at a time for each word in the sentence.\n" +
                "Example: \"I love teaching.\"\n" +
                "1. \"_ love teaching.\"\n" +
                "2. \"I ____ teaching.\"\n" +
                "3. \"I love ________.\""
    )
)