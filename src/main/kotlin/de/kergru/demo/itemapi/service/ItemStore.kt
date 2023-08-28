package de.kergru.demo.itemapi.service

import de.kergru.demo.itemapi.model.Item

interface ItemStore {

    fun findItemById(id: String): Item?

    fun saveItem(item: Item): Item

    fun deleteItem(item: Item)

    fun clear()
}
