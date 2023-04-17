package com.cy.cookietwo.shop

data class ShopModel(
    val id: Int,
    val text: String?,
    val name: String?,
    val value: Int,
    val textColour: String? = null,
    val icon: String? = null,
    val userName: String? = null
)
