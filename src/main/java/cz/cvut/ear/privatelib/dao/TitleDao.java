package cz.cvut.ear.privatelib.dao;

import cz.cvut.ear.privatelib.model.Author;
import cz.cvut.ear.privatelib.model.Genre;
import cz.cvut.ear.privatelib.model.Title;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class TitleDao extends AbstractDao<Title>{
    public TitleDao() {
        super(Title.class);
    }


    /**
     * @return all the titles in library
     */
    public List<Title> getAll() {
        return manager.createQuery("SELECT t FROM Title t WHERE t.removed = FALSE", Title.class).getResultList();
    }


    /**
     * @param genre - genre od title
     * @return all the titles in library with this genre
     */
    public List<Title> findAllByGenre(Genre genre) {
        Objects.requireNonNull(genre);
        return manager.createNamedQuery("Title.findAllByGenre", Title.class).setParameter("genre", genre)
                .getResultList();
    }


    /**
     * @param author - author of title
     * @return all the titles in library with this author
     */
    public List<Title> findAllTitlesBySingleAuthor(Author author){
        Objects.requireNonNull(author);
        return manager.createNamedQuery("Title.findByAuthor", Title.class).setParameter("author", author)
                .getResultList();
    }


    /**
     * @param authors - authors of title
     * @return all the titles in library with these authors
     */
    public List<Title> findAllTitlesByMultipleAuthors(List<Author> authors){
        Objects.requireNonNull(authors);
        int minAuthorCount = authors.size();
        return manager.createNamedQuery("Title.findAllByMultipleAuthors", Title.class)
                .setParameter("authors", authors)
                .setParameter("minAuthorCount", minAuthorCount)
                .getResultList();
    }

}
