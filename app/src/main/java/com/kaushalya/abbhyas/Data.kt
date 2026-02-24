package com.kaushalya.abbhyas

enum class Category(val title: String) {
    SINGLE_LETTER_CAPITAL("S I N G L E   L E T T E R S"),
    SINGLE_LETTER_SMALL("s i n g l e   l e t t e r s"),
    TWO_LETTER_CAPITAL("2 LE TT ER WO RD S"),
    TWO_LETTER_SMALL("2 le tt er wo rd s"),
    THREE_LETTER_CAPITAL("3 LET TER WOR DS"),
    THREE_LETTER_SMALL("3 let ter wor " +
            "ds"),
    NUMBERS("Numbers 1-50")
}

object StudyData {
    fun getRandomQuestions(category: Category, count: Int = 10): List<String> {
        val twoLetterWords = listOf("at","an","in","on","up","go","no","me","we","to","be","do","he","it","my","so","by","if")
        val threeLetterWords = listOf("cat","dog","hat","bat","sun","run","fun","man","pan","pig","bed","red","leg","bus","top")

        val all = when (category) {
            Category.SINGLE_LETTER_CAPITAL -> ('A'..'Z').map { it.toString() }
            Category.SINGLE_LETTER_SMALL -> ('a'..'z').map { it.toString() }
            Category.TWO_LETTER_CAPITAL -> twoLetterWords.map { it.uppercase() }
            Category.TWO_LETTER_SMALL -> twoLetterWords
            Category.THREE_LETTER_CAPITAL -> threeLetterWords.map { it.uppercase() }
            Category.THREE_LETTER_SMALL -> threeLetterWords
            Category.NUMBERS -> (1..50).map { it.toString() }
        }
        return all.shuffled().take(count)
    }
}