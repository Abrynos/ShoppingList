package pl.edu.pjwstk.s999844.shoppinglist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pl.edu.pjwstk.s999844.shoppinglist.dal.ShoppingListDao
import pl.edu.pjwstk.s999844.shoppinglist.dal.ShoppingListDatabase
import pl.edu.pjwstk.s999844.shoppinglist.models.RequiredItem
import pl.edu.pjwstk.s999844.shoppinglist.recyclerviewadapters.ShoppingListAdapter

class MainActivity : AbstractShoppingActivity() {
	private val shoppingListDao: ShoppingListDao by lazy { ShoppingListDatabase.getInstance(applicationContext).getShoppingListDao() }

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		mainListRecyclerView.layoutManager = LinearLayoutManager(this)
		mainListRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

		shoppingListDao.findAllItems().observe(this, this::observeDatabaseChange)
		mainListRecyclerView.adapter = ShoppingListAdapter(this::changeItemCallback)
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

	private fun <T : Activity> openActivity(clazz: Class<T>) = startActivity(Intent(baseContext, clazz))

	private fun changeItemCallback(item: RequiredItem, change: Int) {
		val dbItem: RequiredItem = shoppingListDao.findById(item.id)
			?: return

		val newAmount: Int = dbItem.amount + change
		if (newAmount <= 0) {
			shoppingListDao.delete(dbItem)
		} else {
			dbItem.amount = newAmount
			shoppingListDao.update(dbItem)
		}
	}

	private fun observeDatabaseChange(items: List<RequiredItem>) {
		(mainListRecyclerView.adapter as ShoppingListAdapter).setItems(items)
		if (items.isEmpty()) {
			mainListRecyclerView.visibility = View.GONE
			mainEmptyTextView.visibility = View.VISIBLE
		} else {
			mainListRecyclerView.visibility = View.VISIBLE
			mainEmptyTextView.visibility = View.GONE
		}
	}
}
