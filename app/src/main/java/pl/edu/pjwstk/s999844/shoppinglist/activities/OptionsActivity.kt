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

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import androidx.core.net.toUri
import pl.edu.pjwstk.s999844.shoppinglist.R
import pl.edu.pjwstk.s999844.shoppinglist.adapters.DescriptiveSettingSpinnerAdapter
import pl.edu.pjwstk.s999844.shoppinglist.adapters.SpinnerItemSelectedListener
import pl.edu.pjwstk.s999844.shoppinglist.databinding.ActivityOptionsBinding
import pl.edu.pjwstk.s999844.shoppinglist.settings.Settings
import pl.edu.pjwstk.s999844.shoppinglist.viewBinding

class OptionsActivity : AbstractShoppingActivity() {
	companion object {
		private const val RELEASES_PAGE_LINK = "https://github.com/Abrynos/ShoppingList/releases"

		private const val LATEST_RELEASE_LINK = "$RELEASES_PAGE_LINK/latest"
		private val LATEST_RELEASE_URI: Uri = LATEST_RELEASE_LINK.toUri()
	}

	private val currentReleaseLink: String by lazy { "$RELEASES_PAGE_LINK/tag/${getString(R.string.versionName)}" }
	private val currentReleaseUri: Uri by lazy { currentReleaseLink.toUri() }

	private val binding by viewBinding(ActivityOptionsBinding::inflate)

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(binding.root)

		binding.listOrderDropdown.adapter = DescriptiveSettingSpinnerAdapter(this, Settings.Order.values())
		binding.listOrderDropdown.onItemSelectedListener = SpinnerItemSelectedListener<Settings.Order>(binding.listOrderDropdown) {
			if (settings.order == it) {
				return@SpinnerItemSelectedListener
			}

			settings.order = it
		}

		binding.accentColorDropdown.adapter = DescriptiveSettingSpinnerAdapter(this, Settings.AccentColor.values())
		binding.accentColorDropdown.onItemSelectedListener = SpinnerItemSelectedListener<Settings.AccentColor>(binding.accentColorDropdown) {
			if (settings.accentColor == it) {
				return@SpinnerItemSelectedListener
			}

			settings.accentColor = it
			recreate()
		}
	}

	override fun onStart() {
		super.onStart()

		binding.optionsThemeSwitch.isChecked = settings.darkThemeActive
		binding.listOrderDropdown.setSelection(getIndex(binding.listOrderDropdown, settings.order))
		binding.accentColorDropdown.setSelection(getIndex(binding.accentColorDropdown, settings.accentColor))
		binding.optionsOrderZeroItemsLastSwitch.isChecked = settings.orderZeroItemsLast

		title = getString(R.string.optionsTitleBarText)
	}

	@Suppress("UNUSED_PARAMETER")
	fun onClickThemeSwitch(view: View) {
		val isDark = binding.optionsThemeSwitch.isChecked
		settings.darkThemeActive = isDark
		setDark(isDark)
	}

	@Suppress("UNUSED_PARAMETER")
	fun onClickOrderZeroItemsLastSwitch(view: View) {
		settings.orderZeroItemsLast = binding.optionsOrderZeroItemsLastSwitch.isChecked
	}

	@Suppress("UNUSED_PARAMETER")
	fun onClickCurrentVersion(view: View) = openUriInBrowser(currentReleaseUri)

	@Suppress("UNUSED_PARAMETER")
	fun onClickLatestRelease(view: View) = openUriInBrowser(LATEST_RELEASE_URI)

	private fun openUriInBrowser(uri: Uri) = startActivity(Intent(Intent.ACTION_VIEW, uri))

	private fun <T> getIndex(spinner: Spinner, order: T): Int {
		for (i in 0..spinner.count) {
			@Suppress("UNCHECKED_CAST")
			val valueAtPosition: T = spinner.getItemAtPosition(i) as T
			if (valueAtPosition == order) {
				return i
			}
		}

		return -1
	}
}
