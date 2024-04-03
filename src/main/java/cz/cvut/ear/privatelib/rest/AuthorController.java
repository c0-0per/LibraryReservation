package cz.cvut.ear.privatelib.rest;


import cz.cvut.ear.privatelib.model.Author;
import cz.cvut.ear.privatelib.model.Title;
import cz.cvut.ear.privatelib.service.AuthorService;
import cz.cvut.ear.privatelib.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;
    private final TitleService titleService;

    @Autowired
    public AuthorController(AuthorService authorService, TitleService titleService) {
        this.authorService = authorService;
        this.titleService = titleService;
    }

    @GetMapping//тут че в скобках написать
    public List<Author> getAuthors() {
        return authorService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Void> createAuthor(@RequestBody Author author) {
        authorService.persist(author);
        return ResponseEntity.ok().build();
    }

    //нужно ли вообще нам єти функции????? теперь
    // просто когда добавляешь книгу то сразу єта таблица заполняеться
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{authorId}/titles/{titleId}")
    public ResponseEntity<Void> addTitleToAuthor(@PathVariable Integer authorId, @PathVariable Integer titleId) {
        final Author author = authorService.getAuthorById(authorId);
        final Title title = titleService.getEntityById(titleId);
        authorService.addAuthorToTitle(author, title);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{authorId}/titles/{titleId}")
    public ResponseEntity<Void> removeTitleFromAuthor(@PathVariable Integer authorId, @PathVariable Integer titleId) {
        final Author author = authorService.getAuthorById(authorId);
        final Title titleToRemove = titleService.getEntityById(titleId);
        /* if (titleToRemove == null) {
            throw NotFoundException;
        } */
        authorService.removeAuthorFromTitle(author, titleToRemove);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{authorId}/titles")
    public List<Title> getTitlesByAuthor(@PathVariable Integer authorId) {
        Author author = authorService.getAuthorById(authorId);
        return titleService.findAllByAuthor(author);
    }
}
