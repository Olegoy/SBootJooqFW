package com.example.secondjooq.service

import com.example.secondjooq.model.AuthorModel
import com.example.secondjooq.model.AuthorView
import com.example.secondjooq.model.AuthorWithArticles
import com.example.secondjooq.repository.AuthorRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AuthorService(private val authorRepository: AuthorRepository) {

    fun findById(id: Int): AuthorModel {
        return authorRepository.findById(id);
    }

    fun findViewById(id: Int): MutableList<AuthorView> {
        return authorRepository.findByIdWithTitles(id);
    }

    fun findViewWithArticlesById(id: Int): MutableList<AuthorWithArticles> {
        return authorRepository.findByIdWithArticles(id)
    }

    fun findViewWithArticlesByIdWithMap(id: Int): AuthorWithArticles? {
        return authorRepository.findByIdWithArticlesMap(id)
    }

    fun findAll(): MutableList<AuthorModel> {
        return authorRepository.findAll();
    }

    fun create(author: AuthorModel): Int {
        return authorRepository.create(author)
    }
}