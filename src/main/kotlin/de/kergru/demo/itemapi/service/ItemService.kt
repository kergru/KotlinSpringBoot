package de.kergru.demo.itemapi.service

import de.kergru.demo.itemapi.model.Item
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ItemService(@Autowired private val store: ItemStore) {

    fun findItemById(id: String): Item {
        val item = store.findItemById(id)
        item ?: throw NoSuchElementException("Item with id $id not found")
        return item
    }

    fun createAndPersistItem(name: String): Item {
        val item = Item(name = name)
        return store.saveItem( item )
    }

    fun deleteItem(item: Item) = store.deleteItem(item)

}
