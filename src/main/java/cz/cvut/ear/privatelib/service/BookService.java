package cz.cvut.ear.privatelib.service;

import cz.cvut.ear.privatelib.dao.BookDao;
import cz.cvut.ear.privatelib.dto.TitleDto;
import cz.cvut.ear.privatelib.model.Author;
import cz.cvut.ear.privatelib.model.Book;
import cz.cvut.ear.privatelib.model.Genre;
import cz.cvut.ear.privatelib.model.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BookService {
    private final BookDao dao;

    @Autowired
    public BookService(BookDao dao) {
        this.dao = dao;
    }

    @Transactional(readOnly = true)
    public Book find(Integer id) {
        return dao.find(id);
    }


    @Transactional
    public Book persist(Book book){
        return dao.save(book);
    }
}
