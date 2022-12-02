package com.example.secondjooq.repository

import com.example.secondjooq.mapper.AuthorFromRecordMapper
import com.example.secondjooq.model.ArticleModel
import com.example.secondjooq.model.AuthorModel
import com.example.secondjooq.model.AuthorView
import com.example.secondjooq.model.AuthorWithArticles
import com.example.secondjooq.model.Tables.ARTICLE
import com.example.secondjooq.model.Tables.AUTHOR
import com.example.secondjooq.model.tables.Author
import com.example.secondjooq.model.tables.records.AuthorRecord
import org.jooq.DSLContext
import org.jooq.Record
import org.jooq.Result
import org.jooq.impl.DSL.*
import org.springframework.stereotype.Repository
import java.util.stream.Collectors

@Repository
class AuthorRepository(private val context: DSLContext, private val mapper: AuthorFromRecordMapper) {
    fun findById(id: Int): AuthorModel = context.selectFrom(AUTHOR).where(AUTHOR.ID.eq(id))
            .fetchInto(AuthorModel::class.java).first()

    fun findBy1IdWithArticles(id: Int): MutableList<AuthorView> = context
            .select(AUTHOR.ID, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME, AUTHOR.AGE, ARTICLE.TITLE)
            .from(AUTHOR)
            .join(ARTICLE).on(ARTICLE.AUTHOR_ID.eq(AUTHOR.ID))
            .where(AUTHOR.ID.eq(id))
            .fetchInto(AuthorView::class.java)

    fun findByIdWithTitles(id: Int): MutableList<AuthorView> = context
            .select(
                    AUTHOR.asterisk(),
                    field(
                            select(jsonArrayAgg(ARTICLE.TITLE))
                                    .from(ARTICLE)
                                    .where(ARTICLE.AUTHOR_ID.eq(AUTHOR.ID))
                    ).`as`("titles")
            )
            .from(AUTHOR)
            .where(AUTHOR.ID.eq(id))
            .fetchInto(AuthorView::class.java)

    //не работает
    fun find2ByIdWithArticles(id: Int): MutableList<AuthorWithArticles> = context
            .select(
                    jsonObject(
                            key("id").value(AUTHOR.ID),
                            key("firstName").value(AUTHOR.FIRST_NAME),
                            key("lastName").value(AUTHOR.LAST_NAME),
                            key("age").value(AUTHOR.AGE),
                            key("articles").value(
                                    select(jsonArrayAgg(
                                            jsonObject(
                                                    key("id").value(ARTICLE.ID),
                                                    key("title").value(ARTICLE.TITLE),
                                                    key("description").value(ARTICLE.DESCRIPTION),
                                                    key("authorId").value(ARTICLE.AUTHOR_ID)
                                            )
                                    )
                                    )
                                            .from(ARTICLE)
                                            .where(ARTICLE.AUTHOR_ID.eq(AUTHOR.ID))
                            )))
            .from(AUTHOR)
            .where(AUTHOR.ID.eq(id))
            .fetchInto(AuthorWithArticles::class.java)

    //не работает
    fun find3ByIdWithArticles(id: Int): MutableList<AuthorWithArticles> = context
            .select(
                    AUTHOR.asterisk(),
                    field(
                            select(jsonArrayAgg(jsonObject
                            (
                                    jsonEntry("id", ARTICLE.ID),
                                    jsonEntry("title", ARTICLE.TITLE),
                                    jsonEntry("description", ARTICLE.DESCRIPTION),
                                    jsonEntry("authorId", ARTICLE.AUTHOR_ID))
                            )
                            )
                                    .from(ARTICLE)
                                    .where(ARTICLE.AUTHOR_ID.eq(AUTHOR.ID))
                    ).`as`("articles")
            )
            .from(AUTHOR)
            .fetchInto(AuthorWithArticles::class.java)

    //работает в 2 запроса
    fun f7indByIdWithArticles(id: Int): MutableList<AuthorWithArticles> {
        val authorView: MutableList<AuthorView> = context
                .selectFrom(AUTHOR)
                .where(AUTHOR.ID.eq(id))
                .fetchInto(AuthorView::class.java)

        val articles: MutableList<ArticleModel> = context
                .selectFrom(ARTICLE)
                .where(ARTICLE.AUTHOR_ID.eq(id))
                .fetchInto(ArticleModel::class.java)

        return authorView.stream().map {
            AuthorWithArticles(it.id, it.firstName, it.lastName, it.age, articles.stream().filter { a -> a.authorId == it.id }.collect(Collectors.toList()))
        }.collect(Collectors.toList())
    }

    //2 метода за 1 запрос
    fun findRecordMap(id: Int): MutableMap<AuthorRecord, Result<Record>> {
        return context
                .select()
                .from(AUTHOR)
                .leftJoin(ARTICLE)
                .on(AUTHOR.ID.eq(ARTICLE.AUTHOR_ID))
                .where(AUTHOR.ID.eq(id))
                .fetchGroups(AUTHOR)
    }

    fun findByIdWithArticles(id: Int): MutableList<AuthorWithArticles> {
        return findRecordMap(id)
                .entries.stream()
                .map {
                    AuthorWithArticles(
                            it.key.id, it.key.firstName, it.key.lastName, it.key.age,
                            it.value.stream()
                                    .filter { c -> c.get(ARTICLE.ID) != null }
                                    .map { c -> ArticleModel(
                                            c.get(ARTICLE.ID),
                                            c.get(ARTICLE.TITLE),
                                            c.get(ARTICLE.DESCRIPTION),
                                            c.get(ARTICLE.AUTHOR_ID)) }
                                    .collect(Collectors.toList())
                    )
                }.toList()
    }


    //работает, с применением маппинга
    fun findByIdWithArticlesMap(id: Int): AuthorWithArticles? {
        return context
                .selectFrom(Author.AUTHOR)
                .where(Author.AUTHOR.ID.eq(id))
                .fetchAny()
                ?.let{mapper.map(it)}
    }



    fun findAll(): MutableList<AuthorModel> = context.selectFrom(AUTHOR)
            .fetchInto(AuthorModel::class.java)

    fun create(author: AuthorModel): Int {
        return context.insertInto(AUTHOR)
                .set(AUTHOR.FIRST_NAME, author.firstName)
                .set(AUTHOR.LAST_NAME, author.lastName)
                .set(AUTHOR.AGE, author.age)
                .returning()
                .fetch()
                .single()
                .getValue(AUTHOR.ID)
    }

}