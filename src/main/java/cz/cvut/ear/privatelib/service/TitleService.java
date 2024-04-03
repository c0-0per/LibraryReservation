package cz.cvut.ear.privatelib.service;

import cz.cvut.ear.privatelib.dao.BookDao;
import cz.cvut.ear.privatelib.dao.TitleDao;
import cz.cvut.ear.privatelib.dto.TitleDto;
import cz.cvut.ear.privatelib.model.Author;
import cz.cvut.ear.privatelib.model.Book;
import cz.cvut.ear.privatelib.model.Genre;
import cz.cvut.ear.privatelib.model.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class TitleService {

    private final TitleDao dao;

    private final BookDao bookDao;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    public TitleService(TitleDao dao, BookDao bookDao) {
        this.dao = dao;
        this.bookDao = bookDao;
    }

    @Transactional
    public List<Title> findAll(){
        return dao.getAll();
    }

    @Transactional
    public List<Title> findAllByGenre(Genre genre){
        return dao.findAllByGenre(genre);
    }

    @Transactional
    public List<Title> findAllByAuthor(Author author) {
        return dao.findAllTitlesBySingleAuthor(author);
    }


    @Transactional(readOnly = true)
    public Title getEntityById(Integer id){
        return dao.getEntityById(id);
    }


    @Transactional
    public Book getBookById(Integer id){
        return bookDao.findFirstAvailableByTitleId(id);
    }

    @Transactional
    public Title persist(TitleDto title){
        Genre genre = entityManager.find(Genre.class, title.getGenreId());
        if (genre == null) {
            throw new EntityNotFoundException("Genre not found for ID: " + title.getGenreId());
        }

        List<Author> authors = new ArrayList<>();
        for (Integer authorId : title.getAuthorIds()) {
            Author author = entityManager.find(Author.class, authorId);
            if (author != null) {
                authors.add(author);
            } else {
                throw new EntityNotFoundException("Author not found for ID: " + authorId);
            }
        }

        Title newtitle = title.getTitleFromDto();
        newtitle.setGenre(genre);
        newtitle.setAuthors(authors);
        return dao.save(newtitle);
    }

    @Transactional
    public void update(Title title){
        dao.update(title);
    }

    @Transactional
    public void remove(Title title){
        Objects.requireNonNull(title);
        title.setRemoved(true);
        dao.update(title);
    }

}
