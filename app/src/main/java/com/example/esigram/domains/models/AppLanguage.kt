package com.example.esigram.domains.models

import com.example.esigram.R

enum class AppLanguage(val code: String, val labelResId: Int) {
    SYSTEM(code = "system", labelResId = R.string.language_system),
    FRENCH(code = "fr", labelResId = R.string.language_french),
    ENGLISH(code = "en", labelResId = R.string.language_english),
}