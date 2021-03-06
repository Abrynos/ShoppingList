package pl.edu.pjwstk.s999844.shoppinglist.recyclerviewadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.shopping_list_item.view.*
import pl.edu.pjwstk.s999844.shoppinglist.databinding.ShoppingListItemBinding
import pl.edu.pjwstk.s999844.shoppinglist.models.RequiredItem
import java.util.function.BiConsumer

class ShoppingListAdapter(private val changeAmountCallback: BiConsumer<RequiredItem, Int>) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {

	private var items: List<RequiredItem> = listOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater: LayoutInflater = LayoutInflater.from(parent.context)
		val binding: ShoppingListItemBinding = ShoppingListItemBinding.inflate(inflater)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item: RequiredItem = items[position]

		val binding = holder.shoppingListItem
		binding.amountTextView.text = item.amount.toString()
		binding.amountTextView.isVisible = item.amount > 1

		binding.nameTextView.text = item.name

		binding.buttonLayout.addButton.setOnClickListener {
			changeAmountCallback.accept(item, 1)
		}
		binding.buttonLayout.subtractButton.setOnClickListener {
			changeAmountCallback.accept(item, -1)
		}
		binding.buttonLayout.deleteButton.setOnClickListener {
			changeAmountCallback.accept(item, -item.amount)
		}
	}

	override fun getItemCount(): Int = items.size

	fun setItems(items: List<RequiredItem>) {
		this.items = items
		notifyDataSetChanged()
	}

	class ViewHolder(val shoppingListItem: ShoppingListItemBinding) : RecyclerView.ViewHolder(shoppingListItem.root)
}
