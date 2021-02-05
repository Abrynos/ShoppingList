package pl.edu.pjwstk.s999844.shoppinglist

import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    fun set(dark: Boolean) {
        AppCompatDelegate.setDefaultNightMode(if (dark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }
}