package com.example.secondjooq.model

data class AuthorView(
        val id: Int,
        val firstName: String,
        val lastName: String,
        val age: Int,
        var titles: MutableList<String>? = null
)
