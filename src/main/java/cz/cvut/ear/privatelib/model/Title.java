package cz.cvut.ear.privatelib.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(name = "Title.findAllByGenre",
                query = "SELECT t FROM Title t WHERE t.genre = :genre AND t.removed = FALSE"),
        @NamedQuery(name = "Title.findByAuthor",
                query = "SELECT t FROM Title t JOIN t.authors a WHERE a = :author AND t.removed = FALSE"),
        @NamedQuery(name = "Title.findAllByMultipleAuthors",
                query = "SELECT t FROM Title t JOIN t.authors a WHERE a IN :authors GROUP BY t " +
                        "HAVING COUNT(DISTINCT a) >= :minAuthorCount AND t.removed = FALSE")
})

public class Title { //main class of Books, represent certain book on the website(in app)


    //**
    @Id
    @GeneratedValue
    private Integer id;

    //**
    @Basic(optional = false)
    @Column(nullable = false)
    private String nameOfBook;

    //**
    @Column(nullable = false)
    private String publishier;

    //**
    @Basic(optional = false)
    @Column(nullable = false)
    private String descriptionOfBook;

    //**
    @Basic(optional = false)
    @Column
    private Integer numberOfItems;


    @Basic(optional = false)
    @Column
    private Integer numberOfFreeItems;

    //**
    @Basic(optional = false)
    @Column(nullable = false)
    @Min(1000)
    @Max(2023)
    private Integer publicationYear;

    //**
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "title_author")
    private List<Author> authors;

    //** Genre connection
    @ManyToOne
    @JoinColumn
    @OrderBy("nameOfGenre ASC")
    private Genre genre;


    @OneToMany(mappedBy = "title")
    private List<Review> reviews;

    private Boolean removed = false;
    public Title() {}

    //GETTERS AND SETTERS
    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    public String getNameOfBook() {
        return nameOfBook;
    }

    public void setNameOfBook(String nameOfBook) {
        this.nameOfBook = nameOfBook;
    }

    public String getDescriptionOfBook() {
        return descriptionOfBook;
    }

    public void setDescriptionOfBook(String descriptionOfBook) {
        this.descriptionOfBook = descriptionOfBook;
    }


    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }


    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }


    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public String getPublishier() {
        return publishier;
    }

    public void setPublishier(String publishier) {
        this.publishier = publishier;
    }

    public Integer getNumberOfFreeItems() {
        return numberOfFreeItems;
    }

    public void setNumberOfFreeItems(Integer numberOfFreeItems) {
        this.numberOfFreeItems = numberOfFreeItems;
    }

    public Integer getId() {
        return id;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    //*******************************************************

    /**
     * add a certain author to author's list of title
     * @param author author of title
     */
    public void addAuthor(Author author) {
        Objects.requireNonNull(author);
        if (authors == null) {
            this.authors = new ArrayList<>();
        }
        authors.add(author);
    }

    /**
     * remove a certain author from author's list of title
     * @param author author of title
     */
    public void removeAuthor(Author author) {
        Objects.requireNonNull(author);
        if (authors == null) {
            return;
        }
        authors.removeIf(a -> Objects.equals(a.getId(), author.getId()));
    }

    //надо ли??????
    /**
     * Add a specific genre to the title.
     * @param genre The genre to add.
     */
    public void addGenre(Genre genre) {
        Objects.requireNonNull(genre);
        this.setGenre(genre);
    }

    /**
     * Removes the genre from the title
     */
    public void removeGenre(Genre genre) {
        Objects.requireNonNull(genre);
        this.setGenre(null);
    }


    //**************************************************************************************
    //надо ли??
    /**
     * add a certain review to review's list of title
     * @param review review to add to title
     */
    /* public void addReview(Review review) {
        Objects.requireNonNull(review);
        if (review == null) {
            this.reviews = new ArrayList<>();
        }
        reviews.add(review);
    } */

    //нужно ли вообще будет удалять какое-то ревью? ? ???
    /**
     * remove a certain review from review's list of title
     * @param review review to delete from title
     */
    /* public void removeReview(Review review) {
        Objects.requireNonNull(review);
        if (review == null) {
            return;
        }
        reviews.removeIf(r -> Objects.equals(r.getId(), review.getId()));
    } */

}
