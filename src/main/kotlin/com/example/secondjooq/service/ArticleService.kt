package com.example.secondjooq.service

import com.example.secondjooq.model.ArticleModel
import com.example.secondjooq.repository.ArticleRepository
import org.springframework.stereotype.Service

@Service
class ArticleService(private val articleRepository: ArticleRepository) {

    fun findById(id: Int): ArticleModel {
        return articleRepository.findById(id);
    }

    fun findByAuthorId(id: Int): MutableList<ArticleModel> {
        return articleRepository.findByAuthorId(id);
    }

    fun findAll(): MutableList<ArticleModel> {
        return articleRepository.findAll();
    }

    fun create(article: ArticleModel): Int {
        return articleRepository.create(article)
    }
}