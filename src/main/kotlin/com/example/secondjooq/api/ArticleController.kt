package com.example.secondjooq.api

import com.example.secondjooq.model.ArticleModel
import com.example.secondjooq.service.ArticleService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/kotlin/article")
class ArticleController(private val articleService: ArticleService) {

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Int): ArticleModel {
        return articleService.findById(id);
    }

    @GetMapping("/findByAuthor/{id}")
    fun getByAuthorId(@PathVariable id: Int): MutableList<ArticleModel> {
        return articleService.findByAuthorId(id);
    }

    @GetMapping("")
    fun getAll(): MutableList<ArticleModel> {
        return articleService.findAll();
    }

    @PostMapping("")
    fun create(@RequestBody articleModel: ArticleModel): Int {
        return articleService.create(articleModel);
    }
}