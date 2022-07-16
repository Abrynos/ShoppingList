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

package pl.edu.pjwstk.s999844.shoppinglist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pl.edu.pjwstk.s999844.shoppinglist.adapters.ShoppingListAdapter
import pl.edu.pjwstk.s999844.shoppinglist.dal.ShoppingListDao
import pl.edu.pjwstk.s999844.shoppinglist.dal.ShoppingListDatabase
import pl.edu.pjwstk.s999844.shoppinglist.models.RequiredItem
import pl.edu.pjwstk.s999844.shoppinglist.settings.Settings

class MainActivity : AbstractShoppingActivity() {
	private val shoppingListDao: ShoppingListDao by lazy { ShoppingListDatabase.getInstance(applicationContext).getShoppingListDao() }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		mainListRecyclerView.layoutManager = LinearLayoutManager(this)
		mainListRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

		shoppingListDao.findAllItems().observe(this, this::observeDatabaseChange)
		mainListRecyclerView.adapter = ShoppingListAdapter(this::changeItemCallback)
	}

	override fun onStart() {
		super.onStart()

		supportActionBar?.setDisplayShowHomeEnabled(true)
		supportActionBar?.setDisplayUseLogoEnabled(true)
	}


	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.menu_main, menu)
		return true
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		when (item.itemId) {
			R.id.actionBarMenuOptionsEntry -> {
				openActivity(OptionsActivity::class.java)
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}

	@Suppress("UNUSED_PARAMETER")
	fun onClickFloatingButton(view: View) = openActivity(AddItemActivity::class.java)

	private fun <T : Activity> openActivity(clazz: Class<T>) = startActivity(Intent(baseContext, clazz))

	private fun changeItemCallback(item: RequiredItem, change: Int) {
		val dbItem: RequiredItem = shoppingListDao.findById(item.id)
			?: return

		val newAmount: Int = dbItem.amount + change
		if (newAmount <= 0) {
			shoppingListDao.delete(dbItem)
		} else {
			dbItem.amount = newAmount
			shoppingListDao.update(dbItem)
		}
	}

	private fun observeDatabaseChange(items: List<RequiredItem>) {
		val orderedItems = when (settings.order) {
			Settings.Order.Alphabetical -> items.sortedBy { it.name.lowercase() }
			Settings.Order.AmountAscending -> items.sortedBy { it.amount }
			Settings.Order.AmountDescending -> items.sortedByDescending { it.amount }
			else -> items
		}
		(mainListRecyclerView.adapter as ShoppingListAdapter).setItems(orderedItems)
		mainEmptyTextView.isVisible = items.isEmpty()
		mainListRecyclerView.isVisible = items.isNotEmpty()
	}
}
