package cz.cvut.ear.privatelib.model;

import javax.persistence.*;

import java.util.List;

@Entity
public class Genre {

    @Id
    @GeneratedValue
    private Integer id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String nameOfGenre;

    public String getNameOfGenre() {
        return nameOfGenre;
    }

    public void setNameOfGenre(String nameOfGenre) {
        this.nameOfGenre = nameOfGenre;
    }


}
