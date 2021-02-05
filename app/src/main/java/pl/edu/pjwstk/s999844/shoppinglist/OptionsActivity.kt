package pl.edu.pjwstk.s999844.shoppinglist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
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
        optionsThemeSwitch.setOnClickListener {
            it as SwitchCompat
            onThemeSwitched(it.isChecked)
        }

        title = getString(R.string.optionsTitleBarText)
    }

    private fun onThemeSwitched(isDark: Boolean) {
        settings.darkThemeActive = isDark
        ThemeManager.set(isDark)
    }
}
