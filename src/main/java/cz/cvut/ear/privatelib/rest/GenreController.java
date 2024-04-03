package cz.cvut.ear.privatelib.rest;


import cz.cvut.ear.privatelib.model.Genre;
import cz.cvut.ear.privatelib.model.Title;
import cz.cvut.ear.privatelib.service.GenreService;
import cz.cvut.ear.privatelib.service.TitleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {
    private final GenreService genreService;
    private final TitleService titleService;

    public GenreController(GenreService genreService, TitleService titleService) {
        this.genreService = genreService;
        this.titleService = titleService;
    }

    @GetMapping//тут че в скобках написать
    public List<Genre> getGenres() {
        return genreService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> createGenre(@RequestBody Genre genre) {
        genreService.persist(genre);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{genreId}/title/{titleId}")
    public ResponseEntity<Void> addTitleToGenre(@PathVariable Integer genreId, @PathVariable Integer titleId) {
        final Genre genre = genreService.getGenreById(genreId);
        final Title title = titleService.getEntityById(titleId);
        genreService.addGenreToTitle(genre, title);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{authorId}/title/{titleId}")
    public ResponseEntity<Void> removeTitleFromGenre(@PathVariable Integer genreId, @PathVariable Integer titleId) {
        final Genre genre = genreService.getGenreById(genreId);
        final Title titleToRemove = titleService.getEntityById(titleId);
        if (titleToRemove == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Title not found");
        }
        genreService.removeGenreFromTitle(genre, titleToRemove);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{genreId}/genres")
    public List<Title> getTitlesByGenre(@PathVariable Integer genreId) {
        Genre genre = genreService.getGenreById(genreId);
        return titleService.findAllByGenre(genre);
    }
}
