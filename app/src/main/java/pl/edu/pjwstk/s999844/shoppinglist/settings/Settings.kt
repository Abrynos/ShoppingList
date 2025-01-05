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
 * Copyright (C) 2021-2025 Sebastian Göls
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
		private const val ACCENT_NAME = "accentColor"
		private val ACCENT_DEFAULT: AccentColor = AccentColor.Blue

		private const val IS_DARK_THEME_NAME = "darkTheme"
		private const val IS_DARK_THEME_DEFAULT = true

		private const val ORDER_NAME = "listOrder"
		private val ORDER_DEFAULT: Order = Order.Unordered

		private const val ORDER_ZERO_ITEMS_LAST_NAME = "orderZeroItemsLast"
		private const val ORDER_ZERO_ITEMS_LAST_DEFAULT = true
	}

	private val sharedPreferences: SharedPreferences = context.getSharedPreferences("SETTINGS", MODE_PRIVATE)

	private fun edit(): SharedPreferences.Editor = sharedPreferences.edit()

	var accentColor: AccentColor
		get() {
			val value: Int = sharedPreferences.getInt(ACCENT_NAME, ACCENT_DEFAULT.value)
			return AccentColor.values().firstOrNull {
				it.value == value
			} ?: ACCENT_DEFAULT
		}
		set(value) = edit().putInt(ACCENT_NAME, value.value).apply()

	var darkThemeActive: Boolean
		get() = sharedPreferences.getBoolean(IS_DARK_THEME_NAME, IS_DARK_THEME_DEFAULT)
		set(value) = edit().putBoolean(IS_DARK_THEME_NAME, value).apply()

	var order: Order
		get() {
			val value: Int = sharedPreferences.getInt(ORDER_NAME, ORDER_DEFAULT.value)
			return Order.values().firstOrNull {
				it.value == value
			} ?: ORDER_DEFAULT
		}
		set(value) = edit().putInt(ORDER_NAME, value.value).apply()

	var orderZeroItemsLast: Boolean
		get() = sharedPreferences.getBoolean(ORDER_ZERO_ITEMS_LAST_NAME, ORDER_ZERO_ITEMS_LAST_DEFAULT)
		set(value) = edit().putBoolean(ORDER_ZERO_ITEMS_LAST_NAME, value).apply()

	interface DescriptiveSetting {
		val descriptionResourceId: Int
	}

	enum class AccentColor(val value: Int, val styleResourceId: Int, private val description: Int) : DescriptiveSetting {
		Blue(0, R.style.Theme_ShoppingList_AccentBlue, R.string.optionsAccentBlue),
		Red(1, R.style.Theme_ShoppingList_AccentRed, R.string.optionsAccentRed),
		Orange(2, R.style.Theme_ShoppingList_AccentOrange, R.string.optionsAccentOrange),
		Teal(3, R.style.Theme_ShoppingList_AccentTeal, R.string.optionsAccentTeal),
		Purple(4, R.style.Theme_ShoppingList_AccentPurple, R.string.optionsAccentPurple),
		Green(5, R.style.Theme_ShoppingList_AccentGreen, R.string.optionsAccentGreen);

		override val descriptionResourceId: Int
			get() = description
	}

	enum class Order(val value: Int, private val description: Int) : DescriptiveSetting {
		Unordered(0, R.string.optionsUnorderedOrdering),
		Alphabetical(1, R.string.optionsAlphabeticalOrdering),
		AmountAscending(2, R.string.optionsAmountAscendingOrdering),
		AmountDescending(3, R.string.optionsAmountDescendingOrdering);

		override val descriptionResourceId: Int
			get() = description
	}
}
