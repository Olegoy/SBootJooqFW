package com.example.secondjooq.mapper

import com.example.secondjooq.model.AuthorWithArticles
import com.example.secondjooq.model.tables.records.AuthorRecord
import com.example.secondjooq.repository.ArticleRepository
import org.jooq.RecordMapper
import org.springframework.stereotype.Component

@Component
class AuthorFromRecordMapper(private val articleRepository: ArticleRepository): RecordMapper<AuthorRecord, AuthorWithArticles> {
    override fun map(record: AuthorRecord): AuthorWithArticles {
        var author: AuthorWithArticles = record.into(AuthorWithArticles::class.java)
        author.articles = articleRepository.findByAuthorId(author.id)
        return author
    }
}