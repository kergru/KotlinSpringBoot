package de.kergru.demo.itemapi

import de.kergru.demo.itemapi.service.IdGenerator
import de.kergru.demo.itemapi.service.InMemoryItemStore
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Config {

    @Bean
    fun itemStore() = InMemoryItemStore(idGenerator())

    @Bean
    fun idGenerator() = IdGenerator()
}