package de.kergru.demo.itemapi.service

import de.kergru.demo.itemapi.model.Item
import org.springframework.beans.factory.annotation.Autowired

class InMemoryItemStore(@Autowired private val idGenerator: IdGenerator) : ItemStore{

    val items: MutableList<Item> = mutableListOf();

    override fun findItemById(id: String): Item? {
        return items.firstOrNull { it.id.equals(id) }
    }

    override fun saveItem(item: Item): Item {
        var id = item.id
        if(id.isNullOrBlank()) {
            item.id = idGenerator.generateId()
        }
        items.add(item)
        return item
    }

    override fun deleteItem(item: Item) {
        items.remove(item)
    }

    override fun clear() {
        items.clear()
    }

}
