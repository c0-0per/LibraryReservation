package cz.cvut.ear.privatelib.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;



@Entity
public class Reservation {



    //**
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private LocalDateTime created;


    @Column
    private Boolean approvedByLibrarianBecauseItWasPhysicallyTaken = false;

    //**
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "reservation_id")
    private List<Book> items;

    //**
    @ManyToOne
    @JoinColumn(nullable = false)
    private User customer;


    //GETTERS AND SETTERS
    public Integer getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public Boolean getApprovedByLibrarianBecauseItWasPhysicallyTaken() {
        return approvedByLibrarianBecauseItWasPhysicallyTaken;
    }

    public void setApprovedByLibrarianBecauseItWasPhysicallyTaken(Boolean approvedByLibrarianBecauseItWasPhysicallyTaken) {
        this.approvedByLibrarianBecauseItWasPhysicallyTaken = approvedByLibrarianBecauseItWasPhysicallyTaken;
    }

    public List<Book> getItems() {
        return items;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public void addBook(Book book) {
        items.add(book);
    }

    //*****************************************


}
