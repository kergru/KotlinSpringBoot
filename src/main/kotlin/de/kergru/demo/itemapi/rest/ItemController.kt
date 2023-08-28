package de.kergru.demo.itemapi.rest

import de.kergru.demo.itemapi.model.Item
import de.kergru.demo.itemapi.service.ItemService
import jakarta.validation.Constraint
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/items")
@Validated
class ItemController(@Autowired private val itemService: ItemService) {

    @GetMapping("/{id}")
    fun getItemById(@PathVariable("id") id: String): ResponseEntity<Item> {
        val item = itemService.findItemById(id)
        return ResponseEntity.ok(item)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addItem(@Valid @RequestBody item: Item) = itemService.createAndPersistItem(item.name)

    @DeleteMapping("/{id}")
    fun deleteItemById(@PathVariable("id") id: String): ResponseEntity<Unit> {
        val item = itemService.findItemById(id)
        itemService.deleteItem(item)
        return ResponseEntity.noContent().build()
    }
}