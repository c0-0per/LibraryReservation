package cz.cvut.ear.privatelib.service;


import cz.cvut.ear.privatelib.dao.AuthorDao;
import cz.cvut.ear.privatelib.dao.TitleDao;
import cz.cvut.ear.privatelib.model.Author;
import cz.cvut.ear.privatelib.model.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class AuthorService {
    private final AuthorDao dao;
    private final TitleDao titleDao;

    @Autowired
    public AuthorService(AuthorDao dao, TitleDao titleDao) {
        this.dao = dao;
        this.titleDao = titleDao;
    }

    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return dao.getAll();
    }

    @Transactional(readOnly = true)
    public Author getAuthorById(Integer id) {
        return dao.getEntityById(id);
    }

    @Transactional
    public void persist(Author author) {
        Objects.requireNonNull(author);
        dao.persist(author);
    }

    /**
     * Adds the certain title to the specified author
     * @param author target author
     * @param title title (book) to add
     */
    @Transactional
    public void addAuthorToTitle(Author author, Title title) {
        Objects.requireNonNull(author);
        Objects.requireNonNull(title);
        title.addAuthor(author);
        titleDao.update(title);
    }

    /**
     * Removes the certain title from the certain author
     * @param author author to remove from the title
     * @param title title (book) to remove
     */
    @Transactional
    public void removeAuthorFromTitle(Author author, Title title) {
        Objects.requireNonNull(author);
        Objects.requireNonNull(title);
        title.removeAuthor(author);
        titleDao.update(title);
    }
}
