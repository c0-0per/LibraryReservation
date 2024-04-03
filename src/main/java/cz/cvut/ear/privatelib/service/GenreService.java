package cz.cvut.ear.privatelib.service;

import cz.cvut.ear.privatelib.dao.GenreDao;
import cz.cvut.ear.privatelib.dao.TitleDao;
import cz.cvut.ear.privatelib.model.Author;
import cz.cvut.ear.privatelib.model.Genre;
import cz.cvut.ear.privatelib.model.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class GenreService {
    private final GenreDao genreDao;
    private final TitleDao titleDao;

    @Autowired
    public GenreService(GenreDao genreDao, TitleDao titleDao) {
        this.genreDao = genreDao;
        this.titleDao = titleDao;
    }

    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genreDao.getAll();
    }

    @Transactional(readOnly = true)
    public Genre getGenreById(Integer id) {
        return genreDao.getEntityById(id);
    }

    /**
     * Adds the certain title to the specified genre
     * @param genre target genre
     * @param title title (book) to add
     */
    @Transactional
    public void addGenreToTitle(Genre genre, Title title){
        Objects.requireNonNull(genre);
        Objects.requireNonNull(title);
        title.addGenre(genre);
        titleDao.update(title);
    }

    /**
     * Removes the certain title from the certain genre
     * @param genre genre to remove from the title
     * @param title title (book) to remove
     */
    @Transactional
    public void removeGenreFromTitle(Genre genre, Title title) {
        Objects.requireNonNull(genre);
        Objects.requireNonNull(title);
        title.removeGenre(genre);
        titleDao.update(title);
    }

    @Transactional
    public void persist(Genre genre) {
        Objects.requireNonNull(genre);
        genreDao.persist(genre);
    }


}
