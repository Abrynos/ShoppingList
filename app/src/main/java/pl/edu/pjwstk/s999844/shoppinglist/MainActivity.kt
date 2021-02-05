package pl.edu.pjwstk.s999844.shoppinglist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pl.edu.pjwstk.s999844.shoppinglist.dal.ShoppingListDao
import pl.edu.pjwstk.s999844.shoppinglist.dal.ShoppingListDatabase
import pl.edu.pjwstk.s999844.shoppinglist.models.RequiredItem
import pl.edu.pjwstk.s999844.shoppinglist.recyclerviewadapters.ShoppingListAdapter
import pl.edu.pjwstk.s999844.shoppinglist.settings.Settings


class MainActivity : AppCompatActivity() {
    private val settings: Settings by lazy { Settings(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initItemList()
    }

    private fun initItemList() {
        mainListRecyclerView.layoutManager = LinearLayoutManager(this)
        mainListRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )

        val shoppingListDao: ShoppingListDao =
            ShoppingListDatabase.getInstance(applicationContext).getShoppingListDao();
        shoppingListDao.findAllItems().observe(this, {
            it.let {
                (mainListRecyclerView.adapter as ShoppingListAdapter).setItems(it)
                if (it.isEmpty()) {
                    mainListRecyclerView.visibility = View.GONE
                    mainEmptyTextView.visibility = View.VISIBLE
                } else {
                    mainListRecyclerView.visibility = View.VISIBLE
                    mainEmptyTextView.visibility = View.GONE
                }
            }
        })
        mainListRecyclerView.adapter = ShoppingListAdapter { item, change ->
            val dbItem: RequiredItem = shoppingListDao.findById(item.id)
                ?: return@ShoppingListAdapter

            val newAmount: Int = dbItem.amount + change
            if (newAmount <= 0) {
                shoppingListDao.delete(item)
            } else {
                item.amount = newAmount
                shoppingListDao.update(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
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

    private fun <T : Activity> openActivity(clazz: Class<T>) =
        startActivity(Intent(baseContext, clazz))

    override fun onStart() {
        super.onStart()

        ThemeManager.set(settings.darkThemeActive)
    }
}
