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
import pl.edu.pjwstk.s999844.shoppinglist.R

class Settings(context: Context) {
	companion object {
		private const val IS_DARK_THEME_NAME = "darkTheme"
		private const val IS_DARK_THEME_DEFAULT = true
		private const val ORDER_NAME = "listOrder"
		private val ORDER_DEFAULT: Order = Order.Unordered
	}

	private val sharedPreferences: SharedPreferences = context.getSharedPreferences("SETTINGS", MODE_PRIVATE)

	private fun edit(): SharedPreferences.Editor = sharedPreferences.edit()

	var darkThemeActive: Boolean
		get() = sharedPreferences.getBoolean(IS_DARK_THEME_NAME, IS_DARK_THEME_DEFAULT)
		set(value) = edit().putBoolean(IS_DARK_THEME_NAME, value).apply()

	var order: Order
		get() {
			val value: Int = sharedPreferences.getInt(ORDER_NAME, ORDER_DEFAULT.value)
			return Order.values().firstOrNull {
				it.value == value
			}?: ORDER_DEFAULT
		}
		set(value) = edit().putInt(ORDER_NAME, value.value).apply()


	enum class Order(val value: Int, val descriptionResourceId: Int) {
		Unordered(0, R.string.optionsUnorderedOrdering),
		Alphabetical(1, R.string.optionsAlphabeticalOrdering),
		AmountAscending(2, R.string.optionsAmountAscendingOrdering),
		AmountDescending(3, R.string.optionsAmountDecendingOrdering)
	}
}
