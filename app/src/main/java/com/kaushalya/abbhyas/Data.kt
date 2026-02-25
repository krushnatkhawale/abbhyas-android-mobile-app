package com.kaushalya.abbhyas

enum class Category(val displayName: String) {
    SINGLE_LETTER_CAPITAL("S I N G L E   L E T T E R S"),
    SINGLE_LETTER_SMALL("s i n g l e   l e t t e r s"),
    TWO_LETTER_CAPITAL("2 LE TT ER WO RD S"),
    TWO_LETTER_SMALL("2 le tt er wo rd s"),
    THREE_LETTER_CAPITAL("3 LET TER WOR DS"),
    THREE_LETTER_SMALL("3 let ter wo rd s"),

    MARATHI_VARNMALA("मराठी वर्णमाला"),
    HINDI_VARNMALA("हिंदी वर्णमाला"),
    TWO_LETTER_MARATHI("२ अक्षरी शब्द"),
    THREE_LETTER_MARATHI("३ अक्षरी शब्द"),
    TWO_LETTER_HINDI("२ अक्षरी शब्द"),
    THREE_LETTER_HINDI("३ अक्षरी शब्द"),

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

            Category.MARATHI_VARNMALA -> listOf("अ","आ","इ","ई","उ","ऊ","ए","ऐ","ओ","औ","अं","क","ख","ग","घ","च","छ","ज","झ","ट","ठ","ड","ढ","ण","त","थ","द","ध","न","प","फ","ब","भ","म","य","र","ल","व","श","ष","स","ह","ळ")
            Category.HINDI_VARNMALA -> listOf("अ","आ","इ","ई","उ","ऊ","ऋ","ए","ऐ","ओ","औ","अं","क","ख","ग","घ","च","छ","ज","झ","ट","ठ","ड","ढ","ण","त","थ","द","ध","न","प","फ","ब","भ","म","य","र","ल","व","श","ष","स","ह")
            Category.TWO_LETTER_MARATHI -> listOf("आई","बाबा","घर","दादा","मामा","ताई","काका","आजी","नाना","बाळ","घर","बाग","पाणी","दूध","रोटी")
            Category.THREE_LETTER_MARATHI -> listOf("शाळा","पाणी","सूर्य","चंद्र","फूल","पक्षी","मोर","हत्ती","घोडा","बागा","मुला","ताई","दादा","मामा","आजी")
            Category.TWO_LETTER_HINDI -> listOf("घर","माँ","पापा","कल","मन","दिल","राम","सीता","बच्चा","स्कूल")
            Category.THREE_LETTER_HINDI -> listOf("बच्चा","स्कूल","किताब","रोटी","दूध","सूरज","चाँद","फूल","पानी","घोड़ा")
        }
        return all.shuffled().take(count)
    }

    enum class AppLanguage(val displayName: String, val emoji: String, val color: Long) {
        ENGLISH("English", "🇬🇧", 0xFF2196F3),
        MARATHI("मराठी", "🇮🇳", 0xFF4CAF50),
        HINDI("हिंदी", "🇮🇳", 0xFFFF9800)
    }

    fun getCategoriesForLanguage(language: AppLanguage): List<Category> = when (language) {
        AppLanguage.ENGLISH -> listOf(
            Category.SINGLE_LETTER_CAPITAL,
            Category.SINGLE_LETTER_SMALL,
            Category.TWO_LETTER_CAPITAL,
            Category.TWO_LETTER_SMALL,
            Category.THREE_LETTER_CAPITAL,
            Category.THREE_LETTER_SMALL,
            Category.NUMBERS
        )
        AppLanguage.MARATHI -> listOf(
            Category.MARATHI_VARNMALA,
            Category.TWO_LETTER_MARATHI,
            Category.THREE_LETTER_MARATHI
        )
        AppLanguage.HINDI -> listOf(
            Category.HINDI_VARNMALA,
            Category.TWO_LETTER_HINDI,
            Category.THREE_LETTER_HINDI
        )
    }
}