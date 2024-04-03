package cz.cvut.ear.privatelib.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Loan {

    @Id
    @GeneratedValue
    private Integer id;

    @OneToMany
    @JoinColumn(name = "loan_id")
    private List<Book> items;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User customer;

    @Basic(optional = false)
    private LocalDateTime dueDate;

    private Boolean returned = false;
    private Boolean overDue = false;
    private int days;


}
