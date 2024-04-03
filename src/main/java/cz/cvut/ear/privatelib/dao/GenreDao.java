package cz.cvut.ear.privatelib.dao;

import cz.cvut.ear.privatelib.model.Genre;
import org.springframework.stereotype.Repository;

@Repository
public class GenreDao extends AbstractDao<Genre>{
    public GenreDao() {
        super(Genre.class);
    }
}
