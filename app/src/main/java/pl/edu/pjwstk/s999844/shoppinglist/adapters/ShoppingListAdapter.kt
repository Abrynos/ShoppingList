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
 * Copyright (C) 2021-2026 Sebastian Göls
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

package pl.edu.pjwstk.s999844.shoppinglist.adapters

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import pl.edu.pjwstk.s999844.shoppinglist.databinding.ShoppingListItemBinding
import pl.edu.pjwstk.s999844.shoppinglist.models.RequiredItem
import java.util.function.BiConsumer
import java.util.function.Consumer

class ShoppingListAdapter(
	private val changeAmountCallback: BiConsumer<RequiredItem, Int?>,
	private val togglePurchasedCallback: Consumer<RequiredItem>,
	private val featureActive: Boolean
) : RecyclerView.Adapter<ShoppingListAdapter.ViewHolder>() {
	companion object {
		private const val ZERO_ITEMS_OPACITY = 0.3f
	}

	private var items: List<RequiredItem> = listOf()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater: LayoutInflater = LayoutInflater.from(parent.context)
		val binding: ShoppingListItemBinding = ShoppingListItemBinding.inflate(inflater)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item: RequiredItem = items[position]

		val binding = holder.binding
		binding.amountTextView.text = "${item.amount}"
		binding.amountTextView.isVisible = item.amount > 1

		binding.nameTextView.text = item.name

		val isPurchased = featureActive && item.purchased
		val flags = if (isPurchased) binding.nameTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG else binding.nameTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
		binding.nameTextView.paintFlags = flags
		binding.nameTextView.alpha = if (isPurchased) ZERO_ITEMS_OPACITY else if (item.amount > 0) 1f else ZERO_ITEMS_OPACITY

		if (featureActive) {
			binding.root.setOnClickListener {
				togglePurchasedCallback.accept(item)
			}
		} else {
			binding.root.setOnClickListener(null)
		}

		binding.addButton.setOnClickListener {
			changeAmountCallback.accept(item, 1)
		}
		binding.subtractButton.alpha = if (item.amount > 0) 1f else ZERO_ITEMS_OPACITY
		binding.subtractButton.setOnClickListener {
			changeAmountCallback.accept(item, -1)
		}
		binding.deleteButton.setOnClickListener {
			changeAmountCallback.accept(item, null)
		}
	}

	override fun getItemCount(): Int = items.size

	@SuppressLint("NotifyDataSetChanged")
	fun setItems(items: List<RequiredItem>) {
		this.items = items
		notifyDataSetChanged()
	}

	class ViewHolder(val binding: ShoppingListItemBinding) : RecyclerView.ViewHolder(binding.root)
}
