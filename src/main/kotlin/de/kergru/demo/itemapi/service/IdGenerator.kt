package de.kergru.demo.itemapi.service

import java.util.UUID

class IdGenerator {
    fun generateId(): String = UUID.randomUUID().toString()
}
