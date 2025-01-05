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

package pl.edu.pjwstk.s999844.shoppinglist.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import pl.edu.pjwstk.s999844.shoppinglist.R
import pl.edu.pjwstk.s999844.shoppinglist.dal.ShoppingListDao
import pl.edu.pjwstk.s999844.shoppinglist.dal.ShoppingListDatabase
import pl.edu.pjwstk.s999844.shoppinglist.databinding.ActivityAddItemBinding
import pl.edu.pjwstk.s999844.shoppinglist.models.RequiredItem
import pl.edu.pjwstk.s999844.shoppinglist.viewBinding
import java.util.*

class AddItemActivity : AbstractShoppingActivity() {
	private val binding by viewBinding(ActivityAddItemBinding::inflate)

	private val inputMethodManager: InputMethodManager by lazy { getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }

	private val currentLocale: Locale
		get() = baseContext.resources.configuration.locales.get(0)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)
	}

	override fun onStart() {
		super.onStart()

		// german-speaking people capitalize nouns - as a shopping list usually contains nouns we capitalize the first letter automatically. They can still use lowercase manually if they need a pronoun
		when (currentLocale.language) {
			Locale.GERMAN.language -> binding.addItemNameInput.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES or binding.addItemNameInput.inputType
		}
		binding.addItemNameInput.requestFocus()

		title = getString(R.string.addTitleBarText)

		val data: Uri = intent?.data
			?: return

		val nameParameter = data.getQueryParameter(getString(R.string.shareNameParameter))
			?: return
		if (nameParameter.isEmpty() || nameParameter.isBlank()) {
			return
		}

		binding.addItemNameInput.setText(nameParameter, TextView.BufferType.EDITABLE)

		val amountParameter = data.getQueryParameter(getString(R.string.shareAmountParameter)) ?: return
		if (amountParameter.isEmpty() || amountParameter.isBlank()) {
			return
		}

		val amount: Int = try {
			amountParameter.toInt()
		} catch (e: java.lang.NumberFormatException) {
			return
		}

		if (amount > 1) {
			binding.addItemAmountInput.setText("$amount", TextView.BufferType.EDITABLE)
		}
	}

	private fun createShareUri(item: RequiredItem): String {
		var uriBuilder = Uri.Builder()
			.scheme(getString(R.string.shareScheme))
			.authority(getString(R.string.shareHost))
			.path(getString(R.string.sharePath))
			.appendQueryParameter(getString(R.string.shareNameParameter), item.name)
		if (item.amount > 1) {
			uriBuilder = uriBuilder.appendQueryParameter(getString(R.string.shareAmountParameter), item.amount.toString())
		}
		return uriBuilder.toString()
	}

	private fun createAndValidateItem(): RequiredItem? {
		val name: String = binding.addItemNameInput.text.toString().trim()
		if (name.isEmpty()) {
			warnError(getString(R.string.addNameIsEmptyMessage))
			return null
		}

		val amountString = binding.addItemAmountInput.text.toString().trim()
		val amount: Int = if (amountString.isEmpty() || amountString.isBlank()) {
			1
		} else {
			try {
				amountString.toInt()
			} catch (e: NumberFormatException) {
				warnError(getString(R.string.addAmountNotANumberMessage))
				return null
			}
		}

		if (amount <= 0) {
			warnError(getString(R.string.addAmountTooSmallMessage))
			return null
		}

		return RequiredItem(name, amount)
	}

	@Suppress("UNUSED_PARAMETER")
	fun share(view: View) {
		val item = createAndValidateItem() ?: return

		val sendIntent: Intent = Intent().apply {
			action = Intent.ACTION_SEND
			putExtra(Intent.EXTRA_TEXT, getString(R.string.addShareText, createShareUri(item)))
			type = "text/plain"
		}

		val shareIntent = Intent.createChooser(sendIntent, null)
		startActivity(shareIntent)
	}

	@Suppress("UNUSED_PARAMETER")
	fun addItem(view: View) {
		inputMethodManager.hideSoftInputFromWindow(binding.addItemNameInput.windowToken, 0)

		val item = createAndValidateItem() ?: return

		val shoppingListDao: ShoppingListDao = ShoppingListDatabase.getInstance(applicationContext).getShoppingListDao()

		if (shoppingListDao.findByNameLike(item.name).isNotEmpty()) {
			warnError(getString(R.string.addItemAlreadyExistsMessage))
			return
		}

		shoppingListDao.add(item)

		finish()
	}

	private fun warnError(message: String) = Snackbar.make(binding.saveButton, message, Snackbar.LENGTH_SHORT).show()
}
