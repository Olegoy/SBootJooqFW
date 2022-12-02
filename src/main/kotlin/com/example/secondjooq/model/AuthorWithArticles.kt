package com.example.secondjooq.model

data class AuthorWithArticles(
        val id: Int,
        val firstName: String,
        val lastName: String,
        val age: Int,
        var articles: MutableList<ArticleModel>? = null
)
