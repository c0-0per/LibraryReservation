package cz.cvut.ear.privatelib.dao;

import cz.cvut.ear.privatelib.model.Author;
import org.springframework.stereotype.Repository;

@Repository
public class AuthorDao extends AbstractDao<Author>{

    public AuthorDao() {
        super(Author.class);
    }
}
