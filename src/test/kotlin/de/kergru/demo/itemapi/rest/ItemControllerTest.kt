package de.kergru.demo.itemapi.rest

import de.kergru.demo.itemapi.model.Item
import de.kergru.demo.itemapi.service.IdGenerator
import de.kergru.demo.itemapi.service.InMemoryItemStore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.returnResult
import org.springframework.web.reactive.function.BodyInserters
import reactor.test.StepVerifier

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ItemControllerTest(@Autowired private val store: InMemoryItemStore) {

    private val webClient = WebTestClient
        .bindToServer().baseUrl("http://localhost:8080/items").build()

    @MockBean
    private lateinit var idGenerator: IdGenerator

    @BeforeEach
    fun setup() {
        Mockito.`when`(idGenerator.generateId()).thenReturn("3")

        store.clear()
        store.saveItem(Item("1", "Kotlin"))
        store.saveItem(Item("2", "Java"))
        assertEquals(2, store.items.size)
    }

    @Test
    fun getItemById() {
        webClient.get().uri("/1")
            .exchange()
            .expectStatus().isOk
            .returnResult<Item>()
            .consumeWith {
                StepVerifier.create(it.responseBody)
                    .assertNext {
                        assertNotNull(it)
                        assertEquals("1", it.id)
                        assertEquals("Kotlin", it.name)
                    }
                    .verifyComplete()
            }
    }

    @Test
    fun addItem() {
        webClient.post()
            .body(BodyInserters.fromValue(Item(name = "NodeJS")))
            .exchange()
            .expectStatus().isCreated
            .returnResult<Item>()
            .consumeWith {
                StepVerifier.create(it.responseBody)
                    .assertNext {
                        assertNotNull(it)
                        assertEquals("3", it.id)
                        assertEquals("NodeJS", it.name)
                    }
                    .verifyComplete()
            }
    }

    @Test
    fun addItemWithInvalidNameShouldReturnClientError() {
        webClient.post()
            .body(BodyInserters.fromValue(Item(name = "123")))
            .exchange()
            .expectStatus().is4xxClientError
    }

    @Test
    fun deleteItemById() {
        webClient.delete().uri("/1")
            .exchange()
            .expectStatus().isNoContent
    }
}