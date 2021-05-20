package pl.edu.pjwstk.s999844.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import pl.edu.pjwstk.s999844.shoppinglist.settings.Settings

abstract class AbstractShoppingActivity : AppCompatActivity() {
	private val settings: Settings by lazy { Settings(this) }

	override fun onStart() {
		super.onStart()

		ThemeManager.setDark(settings.darkThemeActive)
	}
}