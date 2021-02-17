package pl.edu.pjwstk.s999844.shoppinglist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_options.*
import pl.edu.pjwstk.s999844.shoppinglist.settings.Settings

class OptionsActivity : AppCompatActivity() {
	companion object {
		private const val RELEASES_PAGE_LINK = "https://github.com/Abrynos/ShoppingList/releases/"

		private const val CURRENT_RELEASE_LINK = RELEASES_PAGE_LINK + "tag/" + BuildConfig.VERSION_NAME
		private const val LATEST_RELEASE_LINK = RELEASES_PAGE_LINK + "latest"

		private val CURRENT_RELEASE_URI by lazy { Uri.parse(CURRENT_RELEASE_LINK) }
		private val LATEST_RELEASE_URI by lazy { Uri.parse(LATEST_RELEASE_LINK) }
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
