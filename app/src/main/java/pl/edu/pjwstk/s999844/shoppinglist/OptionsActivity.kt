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
 * Copyright (C) 2021-2021  Sebastian Göls
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
import kotlinx.android.synthetic.main.activity_options.*
import pl.edu.pjwstk.s999844.shoppinglist.settings.Settings

class OptionsActivity : AbstractShoppingActivity() {
	companion object {
		private const val RELEASES_PAGE_LINK = "https://github.com/Abrynos/ShoppingList/releases/"

		private const val CURRENT_RELEASE_LINK = RELEASES_PAGE_LINK + "tag/" + BuildConfig.VERSION_NAME
		private const val LATEST_RELEASE_LINK = RELEASES_PAGE_LINK + "latest"

		private val CURRENT_RELEASE_URI: Uri = Uri.parse(CURRENT_RELEASE_LINK)
		private val LATEST_RELEASE_URI: Uri = Uri.parse(LATEST_RELEASE_LINK)
	}

	private val settings: Settings by lazy { Settings(this) }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_options)
	}

	override fun onStart() {
		super.onStart()

		optionsThemeSwitch.isChecked = settings.darkThemeActive

		title = getString(R.string.optionsTitleBarText)
	}

	@Suppress("UNUSED_PARAMETER")
	fun onClickThemeSwitch(view: View) {
		val isDark = optionsThemeSwitch.isChecked
		settings.darkThemeActive = isDark
		ThemeManager.setDark(isDark)
	}

	@Suppress("UNUSED_PARAMETER")
	fun onClickCurrentVersion(view: View) = openUriInBrowser(CURRENT_RELEASE_URI)

	@Suppress("UNUSED_PARAMETER")
	fun onClickLatestRelease(view: View) = openUriInBrowser(LATEST_RELEASE_URI)

	private fun openUriInBrowser(uri: Uri) = startActivity(Intent(Intent.ACTION_VIEW, uri))
}
