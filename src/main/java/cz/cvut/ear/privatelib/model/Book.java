package cz.cvut.ear.privatelib.model;

import javax.persistence.*;




@Entity
//@PrimaryKeyJoinColumn(name = "book_id")
public class Book {


    @Id
    @GeneratedValue
    private Integer id;


    @ManyToOne(optional = false)
    @JoinColumn(nullable = false)
    private Title title;

    private Boolean reserved = false;

    private Boolean physicallytaken = false;
    private Boolean loaned = false;


    //GETTERS AND SETTERS
    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }
    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }

    public Boolean getPhysicallytaken() {
        return physicallytaken;
    }

    public void setPhysicallytaken(Boolean physicallytaken) {
        this.physicallytaken = physicallytaken;
    }

    public Boolean getLoaned() {
        return loaned;
    }

    public void setLoaned(Boolean loaned) {
        this.loaned = loaned;
    }
    public Integer getId() {
        return id;
    }


    //*************************************************************

}
