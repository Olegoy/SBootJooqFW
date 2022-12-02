package com.example.secondjooq.repository

import com.example.secondjooq.model.ArticleModel
import com.example.secondjooq.model.Tables
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class ArticleRepository(private val context: DSLContext) {
    fun findById(id: Int): ArticleModel = context.selectFrom(Tables.ARTICLE).where(Tables.ARTICLE.ID.eq(id))
            .fetchInto(ArticleModel::class.java).first()

    fun findByAuthorId(id: Int): MutableList<ArticleModel> = context
            .selectFrom(Tables.ARTICLE)
            .where(Tables.ARTICLE.AUTHOR_ID.eq(id))
            .fetchInto(ArticleModel::class.java)

    fun findAll(): MutableList<ArticleModel> = context.selectFrom(Tables.ARTICLE)
            .fetchInto(ArticleModel::class.java)

    fun create(article: ArticleModel): Int {
        return context.insertInto(Tables.ARTICLE)
                .set(Tables.ARTICLE.ID, article.id)
                .set(Tables.ARTICLE.TITLE, article.title)
                .set(Tables.ARTICLE.DESCRIPTION, article.description)
                .set(Tables.ARTICLE.AUTHOR_ID, article.authorId)
                .returning()
                .fetch()
                .single()
                .getValue(Tables.ARTICLE.ID)
    }
}