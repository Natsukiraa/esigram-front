package com.example.esigram.domains.models

import com.example.esigram.R

enum class ThemeMode(val labelResId: Int) {
    System(R.string.theme_system),
    Light(R.string.theme_light),
    Dark(R.string.theme_dark)
}