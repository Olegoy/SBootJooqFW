package com.example.secondjooq.repository;

import java.util.List;

import com.example.secondjooq.model.Au;
import com.example.secondjooq.model.tables.Author;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.example.secondjooq.model.tables.Author.AUTHOR;

@Repository
public class ARep {

    private final DSLContext context;


    public ARep(DSLContext context) {
        this.context = context;
    }

    public List<Au> find() {
        return context.selectFrom(AUTHOR).where(AUTHOR.ID.eq(1)).fetchInto(Au.class);
    }

    public List<Au> findById(Integer id) {
        return context.selectFrom(AUTHOR).where(AUTHOR.ID.eq(id)).fetchInto(Au.class);
    }
}
