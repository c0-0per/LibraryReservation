package cz.cvut.ear.privatelib.rest;

import cz.cvut.ear.privatelib.dto.TitleDto;
import cz.cvut.ear.privatelib.model.Book;
import cz.cvut.ear.privatelib.model.Title;
import cz.cvut.ear.privatelib.service.BookService;
import cz.cvut.ear.privatelib.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/titles")
public class TitleController {
    private final TitleService titleService;
    private final BookService bookservice;

    @Autowired
    public TitleController(TitleService titleService, BookService bookservice) {
        this.titleService = titleService;
        this.bookservice = bookservice;
    }

    /**
     * Shows a certain title (book)
     * @param id id of title(book)
     * @return title(book)
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Title> getTitleById(@PathVariable Integer id) {
        Title title = titleService.getEntityById(id);
        return ResponseEntity.ok(title);
    }



    @GetMapping(value = "/{id}/getbooks")
    public ResponseEntity<Book> getFreeBookByTitle(@PathVariable Integer id) {
        Book book = titleService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Add a new book to a library
     * @param title book to add
     * @return
     */
    //@PreAuthorize("hasRole('ADMIN')") расскоминтируем как проверим что работает без этого
    @PostMapping("/create")
    public ResponseEntity<Title> createTitle(@RequestBody TitleDto title) {
//        Title newTitle = title.getTitleFromDto();
//        titleService.persist(newTitle);
//        return ResponseEntity.ok(newTitle);
        Title createdTitle = titleService.persist(title);

        for(int i = 0; i < title.getNumbersOfItems(); i++) {
            Book book = new Book();
            book.setTitle(createdTitle);
            bookservice.persist(book);
        }
        return new ResponseEntity<>(createdTitle, HttpStatus.CREATED);
    }

    /**
     * Remove a book from the library
     * @param id id of book to remove
     * @return
     */
    //@PreAuthorize("hasRole('ADMIN')") расскоминтируем как проверим что работает без этого
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> removeTitleById(@PathVariable Integer id) {
        Title title = titleService.getEntityById(id);
        if (title == null) {
            return ResponseEntity.notFound().build(); // Если книга не найдена, возвращаем 404
        }
        titleService.remove(title); // Удаление книги
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    public List<Title> getTitles() {
        return titleService.findAll();
    }

}
