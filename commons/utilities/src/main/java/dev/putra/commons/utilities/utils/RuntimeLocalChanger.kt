package dev.putra.commons.utilities.utils

/**
 * Created by Chyrus on 3/28/2020.
 */

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object RuntimeLocalChanger {

    @SuppressLint("DefaultLocale")
    fun updateLocale(context: Context, language: String): Context {
        val savedLocale = Locale(language.toLowerCase())
        Locale.setDefault(savedLocale)
        val configuration = Configuration()
        configuration.setLocale(savedLocale)
        return context.createConfigurationContext(configuration)
    }

}