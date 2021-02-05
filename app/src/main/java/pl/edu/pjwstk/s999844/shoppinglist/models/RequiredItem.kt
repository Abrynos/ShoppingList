package pl.edu.pjwstk.s999844.shoppinglist.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class RequiredItem(var name: String, var amount: Int) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RequiredItem

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }


}
