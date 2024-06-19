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
 * Copyright (C) 2021-2023 Sebastian Göls
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

package pl.edu.pjwstk.s999844.shoppinglist.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import pl.edu.pjwstk.s999844.shoppinglist.R
import pl.edu.pjwstk.s999844.shoppinglist.adapters.ShoppingListAdapter
import pl.edu.pjwstk.s999844.shoppinglist.dal.ShoppingListDao
import pl.edu.pjwstk.s999844.shoppinglist.dal.ShoppingListDatabase
import pl.edu.pjwstk.s999844.shoppinglist.databinding.ActivityMainBinding
import pl.edu.pjwstk.s999844.shoppinglist.models.RequiredItem
import pl.edu.pjwstk.s999844.shoppinglist.viewBinding

class MainActivity : AbstractShoppingActivity() {
	private val binding by viewBinding(ActivityMainBinding::inflate)

	private val shoppingListDao: ShoppingListDao by lazy { ShoppingListDatabase.getInstance(applicationContext).getShoppingListDao() }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		binding.mainListRecyclerView.layoutManager = LinearLayoutManager(this)
		binding.mainListRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

		shoppingListDao.findAllItems().observe(this, this::observeDatabaseChange)
		binding.mainListRecyclerView.adapter = ShoppingListAdapter(this::changeItemCallback)
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
				activityLauncher.launch(Intent(baseContext, OptionsActivity::class.java)) {
					recreate()
				}
				return true
			}
		}
		return super.onOptionsItemSelected(item)
	}

	@Suppress("UNUSED_PARAMETER")
	fun onClickFloatingButton(view: View) = startActivity(Intent(baseContext, AddItemActivity::class.java))

	/**
	 * Update item's amount.
	 *
	 * @param[item] what to change amount of
	 * @param[change] how much it should change, `null` for deleting it
	 */
	private fun changeItemCallback(item: RequiredItem, change: Int?) {
		val dbItem: RequiredItem = shoppingListDao.findById(item.id)
			?: return

		val newAmount = change?.plus(dbItem.amount)
		if (newAmount == null || newAmount <= 0) {
			shoppingListDao.delete(dbItem)
		} else {
			dbItem.amount = newAmount
			shoppingListDao.update(dbItem)
		}
	}

	private fun observeDatabaseChange(items: List<RequiredItem>) {
		(binding.mainListRecyclerView.adapter as ShoppingListAdapter).setItems(items)

		binding.mainEmptyTextView.isVisible = items.isEmpty()
		binding.mainListRecyclerView.isVisible = items.isNotEmpty()
	}
}
