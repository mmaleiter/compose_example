package cut.the.crap.mylibrary.models

import java.util.*

sealed class SearchParam {
    companion object {

        private fun setLanguageCodeOrDefault(locale: Locale = Locale.getDefault()): String {
            return when (locale in validLocales) {
                true -> validCodes[validLocales.indexOf(locale)]
                else -> {
                    val localDefault = Locale.getDefault()
                    if (localDefault in validLocales) validCodes[validLocales.indexOf(localDefault)]
                    else "en"
                }
            }
        }

        val validCodes = listOf(
            "cs", "da", "de", "en", "es", "fr", "id", "it", "hu", "nl", "no", "pl", "pt",
            "ro", "sk", "fi", "sv", "tr", "vi", "th", "bg", "ru", "el", "ja", "ko", "zh"
        )
        val validLocales = validCodes.map { Locale(it) }

        val validColors = listOf(
            "grayscale", "transparent", "red", "orange", "yellow",
            "green", "turquoise", "blue", "lilac", "pink",
            "white", "gray", "black", "brown"
        )

        val validCategories = listOf(
            "backgrounds",
            "fashion",
            "nature",
            "science",
            "education",
            "feelings",
            "health",
            "people",
            "religion",
            "places",
            "animals",
            "industry",
            "computer",
            "food",
            "sports",
            "transportation",
            "travel",
            "buildings",
            "business",
            "music"
        )

        val validImageTypes = listOf(
            "all", "photo", "illustration", "vector"
        )

        val validOrientation = listOf("all", "horizontal", "vertical")
    }

    data class ImageSearchParams(
        val searchText: String = "",
        var languageCode: String = setLanguageCodeOrDefault(),
        val mediaType: MutableList<String> = validImageTypes.takeLast(3).toMutableList(),
        val orientation: MutableList<String> = validOrientation.takeLast(2).toMutableList(),
        val categories: MutableList<String> = validCategories.toMutableList(),
        var minWidth: String = "0",
        var minHeight: String = "0",
        val colors: MutableList<String> = validColors.toMutableList(),
        var editors_choice: String = "false",
        var order: String = "popular",   // "popular", "latest"
        val page: String = "1",
        val perPage: String = "20",  // 3 - 200
    )

    data class VideoSearchParams(
        val searchText: String = "",
        val languageCode: String = setLanguageCodeOrDefault(),
    )
}