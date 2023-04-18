package com.example.week6.utility.others

import android.content.Context
import java.util.Locale

fun updateResources(
    context: Context,
    languageCode: String
): Context {
    val locale = Locale(languageCode).also {
        Locale.setDefault(it)
    }

    val resources = context.resources

    val configuration = resources.configuration.apply {
        this.locale = locale
        setLayoutDirection(locale)
    }

    resources.updateConfiguration(configuration, resources.displayMetrics)
    return context
}