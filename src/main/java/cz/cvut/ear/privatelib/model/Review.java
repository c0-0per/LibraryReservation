package cz.cvut.ear.privatelib.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import java.util.List;
import java.util.Objects;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "Review.findAllByUser", query = "SELECT r FROM Review r WHERE r.user = :user"
        ),
        @NamedQuery(
                name = "Review.findAllByTitle",
                query = "SELECT r FROM Review r WHERE r.title = :title"
        )
})
public class Review {

    @Id
    @GeneratedValue
    private Integer id;

    @Basic(optional = false)
    @Column(nullable = false)
    @Min(1)
    @Max(5)
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    //GETTERS AND SETTERS
    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
    //***************************************************************


}
