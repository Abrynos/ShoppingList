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

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import pl.edu.pjwstk.s999844.shoppinglist.adapters.OrderSpinnerAdapter
import pl.edu.pjwstk.s999844.shoppinglist.databinding.ActivityOptionsBinding
import pl.edu.pjwstk.s999844.shoppinglist.settings.Settings

class OptionsActivity : AbstractShoppingActivity() {
	companion object {
		private const val RELEASES_PAGE_LINK = "https://github.com/Abrynos/ShoppingList/releases/"

		private const val CURRENT_RELEASE_LINK = RELEASES_PAGE_LINK + "tag/" + BuildConfig.VERSION_NAME
		private const val LATEST_RELEASE_LINK = RELEASES_PAGE_LINK + "latest"

		private val CURRENT_RELEASE_URI: Uri = Uri.parse(CURRENT_RELEASE_LINK)
		private val LATEST_RELEASE_URI: Uri = Uri.parse(LATEST_RELEASE_LINK)
	}

	internal val binding by viewBinding(ActivityOptionsBinding::inflate)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		binding.listOrderDropdown.adapter = OrderSpinnerAdapter(this)
		binding.listOrderDropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
				settings.order = binding.listOrderDropdown.getItemAtPosition(position) as Settings.Order
			}

			override fun onNothingSelected(p0: AdapterView<*>?) {
				throw IllegalStateException()
			}
		}
	}

	override fun onStart() {
		super.onStart()

		binding.optionsThemeSwitch.isChecked = settings.darkThemeActive
		binding.listOrderDropdown.setSelection(getIndex(binding.listOrderDropdown, settings.order))

		title = getString(R.string.optionsTitleBarText)
	}

	@Suppress("UNUSED_PARAMETER")
	fun onClickThemeSwitch(view: View) {
		val isDark = binding.optionsThemeSwitch.isChecked
		settings.darkThemeActive = isDark
		ThemeManager.setDark(isDark)
	}

	@Suppress("UNUSED_PARAMETER")
	fun onClickCurrentVersion(view: View) = openUriInBrowser(CURRENT_RELEASE_URI)

	@Suppress("UNUSED_PARAMETER")
	fun onClickLatestRelease(view: View) = openUriInBrowser(LATEST_RELEASE_URI)

	private fun openUriInBrowser(uri: Uri) = startActivity(Intent(Intent.ACTION_VIEW, uri))

	private fun getIndex(spinner: Spinner, order: Settings.Order): Int {
		for (i in 0..spinner.count) {
			val valueAtPosition: Settings.Order = spinner.getItemAtPosition(i) as Settings.Order
			if (valueAtPosition == order) {
				return i
			}
		}

		return -1
	}
}
