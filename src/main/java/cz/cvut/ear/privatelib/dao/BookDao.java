package cz.cvut.ear.privatelib.dao;

import cz.cvut.ear.privatelib.model.Book;
import cz.cvut.ear.privatelib.model.Title;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;

@Repository
public class BookDao extends AbstractDao<Book> {

    @PersistenceContext
    private EntityManager manager;

    protected BookDao() {
        super(Book.class);
    }

    public Book find(Integer id) {
        Objects.requireNonNull(id);
        return manager.find(Book.class, id);
    }

    public Book findFirstAvailableByTitleId(Integer titleId) {
        Objects.requireNonNull(titleId);

        // Sample JPQL query to fetch the first book that is available
        // This query assumes there's a 'title' relationship and an 'available' field in the Book entity
        String jpql = "SELECT b FROM Book b WHERE b.title.id = :titleId AND b.loaned = false AND b.physicallytaken = false AND b.reserved = false";

        return manager.createQuery(jpql, Book.class)
                .setParameter("titleId", titleId)
                .setMaxResults(1)
                .getResultStream()
                .findFirst()
                .orElse(null);
    }

}
