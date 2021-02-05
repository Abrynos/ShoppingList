package pl.edu.pjwstk.s999844.shoppinglist.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Settings(context: Context) {
    companion object {
        private const val isDarkThemeName = "darkTheme"
        private const val isDarkThemeDefault = true
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("SETTINGS", MODE_PRIVATE)

    private fun edit(): SharedPreferences.Editor = sharedPreferences.edit()

    var darkThemeActive: Boolean
        get() = sharedPreferences.getBoolean(isDarkThemeName, isDarkThemeDefault)
        set(value) = edit().putBoolean(isDarkThemeName, value).apply()
}