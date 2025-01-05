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

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.edu.pjwstk.s999844.shoppinglist.models.RequiredItem

@Dao
interface ShoppingListDao {
	@Query("SELECT * FROM RequiredItem")
	fun findAllItems(): LiveData<List<RequiredItem>>

	@Query("SELECT * FROM RequiredItem WHERE name LIKE :name")
	fun findByNameLike(name: String): List<RequiredItem>

	@Query("SELECT * FROM RequiredItem WHERE id LIKE :id")
	fun findById(id: Long): RequiredItem?

	@Insert
	fun add(item: RequiredItem)

	@Update
	fun update(item: RequiredItem)

	@Delete
	fun delete(item: RequiredItem)
}
