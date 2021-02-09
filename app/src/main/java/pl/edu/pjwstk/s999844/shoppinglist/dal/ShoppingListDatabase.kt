package pl.edu.pjwstk.s999844.shoppinglist.dal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.edu.pjwstk.s999844.shoppinglist.models.RequiredItem

@Database(entities = [RequiredItem::class], version = 1)
abstract class ShoppingListDatabase : RoomDatabase() {
	abstract fun getShoppingListDao(): ShoppingListDao

	companion object {
		private var INSTANCE: ShoppingListDatabase? = null

		fun getInstance(context: Context): ShoppingListDatabase {
			var instance = INSTANCE
			if (instance != null) {
				return instance
			}

			instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingListDatabase::class.java,
                    ShoppingListDatabase::class.java.name
            )
					.allowMainThreadQueries()
					.build()
			INSTANCE = instance
			return instance
		}
	}
}
