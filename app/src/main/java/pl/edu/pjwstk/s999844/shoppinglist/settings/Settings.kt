package pl.edu.pjwstk.s999844.shoppinglist.settings

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class Settings(context: Context) {
	companion object {
		private const val IS_DARK_THEME_NAME = "darkTheme"
		private const val IS_DARK_THEME_DEFAULT = true
	}

	private val sharedPreferences: SharedPreferences = context.getSharedPreferences("SETTINGS", MODE_PRIVATE)

	private fun edit(): SharedPreferences.Editor = sharedPreferences.edit()

	var darkThemeActive: Boolean
		get() = sharedPreferences.getBoolean(IS_DARK_THEME_NAME, IS_DARK_THEME_DEFAULT)
		set(value) = edit().putBoolean(IS_DARK_THEME_NAME, value).apply()
}