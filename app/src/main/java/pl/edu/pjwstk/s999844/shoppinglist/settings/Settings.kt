/*
 *  _____ _                       _             _     _     _
 * /  ___| |                     (_)           | |   (_)   | |
 * \ `--.| |__   ___  _ __  _ __  _ _ __   __ _| |    _ ___| |_
 *  `--. \ '_ \ / _ \| '_ \| '_ \| | '_ \ / _` | |   | / __| __|
 * /\__/ / | | | (_) | |_) | |_) | | | | | (_| | |___| \__ \ |_
 * \____/|_| |_|\___/| .__/| .__/|_|_| |_|\__, \_____/_|___/\__|
 *                   | |   | |             __/ |
 *                   |_|   |_|            |___/
 *
 * Copyright (C) 2021-2022 Sebastian Göls
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License only
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

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
