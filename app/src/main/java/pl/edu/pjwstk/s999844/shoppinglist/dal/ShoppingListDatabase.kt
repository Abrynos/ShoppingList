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

package pl.edu.pjwstk.s999844.shoppinglist.dal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.edu.pjwstk.s999844.shoppinglist.models.RequiredItem

@Database(entities = [RequiredItem::class], version = 1, exportSchema = false)
abstract class ShoppingListDatabase : RoomDatabase() {
	abstract fun getShoppingListDao(): ShoppingListDao

	@Suppress("SyntheticAccessor")
	companion object {
		private var INSTANCE: ShoppingListDatabase? = null

		fun getInstance(context: Context): ShoppingListDatabase {
			var instance = INSTANCE
			if (instance != null) {
				return instance
			}

			instance = Room.databaseBuilder(context.applicationContext, ShoppingListDatabase::class.java, ShoppingListDatabase::class.java.name)
				.allowMainThreadQueries()
				.build()
			INSTANCE = instance
			return instance
		}
	}
}
