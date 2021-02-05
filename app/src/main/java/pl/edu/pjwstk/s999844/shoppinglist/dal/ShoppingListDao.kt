package pl.edu.pjwstk.s999844.shoppinglist.dal

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.edu.pjwstk.s999844.shoppinglist.models.RequiredItem

@Dao
interface ShoppingListDao {
    @Query("SELECT * FROM RequiredItem")
    fun findAllItems(): LiveData<List<RequiredItem>>

    @Query("SELECT * FROM RequiredItem WHERE name LIKE :name")
    fun findByNameLike(name: String): List<RequiredItem>

    @Query("SELECT * FROM RequiredItem WHERE id LIKE :id")
    fun findById(id: Long): RequiredItem?

    @Insert
    fun add(item: RequiredItem)

    @Update
    fun update(item: RequiredItem)

    @Delete
    fun delete(item: RequiredItem)
}