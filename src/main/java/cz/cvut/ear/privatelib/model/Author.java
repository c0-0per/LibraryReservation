package cz.cvut.ear.privatelib.model;

import javax.persistence.*;

import java.util.List;

@Entity
public class Author {

    @Id
    @GeneratedValue
    private Integer id;


    @Basic(optional = false)
    @Column(nullable = false)
    private String name;



    //GETTERS AND SETTERS
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //********************************************************************

}
