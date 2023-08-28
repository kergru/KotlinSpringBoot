package de.kergru.demo.itemapi.model

import jakarta.validation.constraints.Pattern


data class Item(
    var id: String?  = null,
    @field:Pattern(regexp = "[a-zA-Z]+") val name: String) {

}
