package com.example.secondjooq.api

import com.example.secondjooq.model.AuthorModel
import com.example.secondjooq.model.AuthorView
import com.example.secondjooq.model.AuthorWithArticles
import com.example.secondjooq.service.AuthorService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kotlin/author")
class AuthorController(private val authorService: AuthorService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): AuthorModel {
        return authorService.findById(id)
    }

    @GetMapping("/view/{id}")
    fun getViewById(@PathVariable id: Int): MutableList<AuthorView> {
        return authorService.findViewById(id)
    }

    @GetMapping("/view/art/{id}")
    fun getViewWithArticlesById(@PathVariable id: Int): MutableList<AuthorWithArticles> {
        return authorService.findViewWithArticlesById(id)
    }

    @GetMapping("/view/art/map/{id}")
    fun getViewWithArticlesByIdWithMap(@PathVariable id: Int): AuthorWithArticles? {
        return authorService.findViewWithArticlesByIdWithMap(id)
    }

    @GetMapping("")
    fun getAll(): MutableList<AuthorModel> {
        return authorService.findAll()
    }

    @PostMapping("")
    fun create(@RequestBody authorModel: AuthorModel): Int {
        return authorService.create(authorModel)
    }
}