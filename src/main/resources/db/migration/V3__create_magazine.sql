CREATE TABLE magazine (
                      id             INT          NOT NULL PRIMARY KEY,
                      title          VARCHAR(100) NOT NULL
);

CREATE TABLE article_magazine (
                             article_id      INT          NOT NULL,
                             magazine_id     INT          NOT NULL,

                             PRIMARY KEY (article_id, magazine_id),
                             CONSTRAINT fk_am_article     FOREIGN KEY (article_id)  REFERENCES article (id)
                                 ON UPDATE CASCADE ON DELETE CASCADE,
                             CONSTRAINT fk_am_magazine       FOREIGN KEY (magazine_id)    REFERENCES magazine (id)
);