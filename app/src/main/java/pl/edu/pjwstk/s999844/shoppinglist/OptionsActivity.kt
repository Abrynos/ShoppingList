package pl.edu.pjwstk.s999844.shoppinglist

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_options.*
import pl.edu.pjwstk.s999844.shoppinglist.settings.Settings

class OptionsActivity : AppCompatActivity() {
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
}
