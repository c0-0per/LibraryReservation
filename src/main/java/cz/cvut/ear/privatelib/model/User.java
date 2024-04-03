package cz.cvut.ear.privatelib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import cz.cvut.ear.privatelib.dao.UserDao;
import cz.cvut.ear.privatelib.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "EAR_USER")
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username")
        //@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.userId = :userId")
})
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    @Basic(optional = false)
    @Column(name ="username", nullable = false, unique = true)
    private String username;

    @Basic(optional = false)
    @Column(name ="password", nullable = false)
    private String password;

    @Column(name = "email")
    private String email;

//    @JsonIgnore
//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
//    private Cart cart;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID") })
    private Set<Role> roles;

    @OneToMany(mappedBy = "user") //перепроверить скобки
    private List<Review> reviews;


    //GETTERS AND SETTERS

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void encodePassword(PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }


//    public Cart getCart() {
//        return cart;
//    }

//    public void setCart(Cart cart) {
//        this.cart = cart;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public Integer getId() {
        return id;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

//    public User getUserFromDto(){
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setEmail(email);
//
//        return user;
//    }
//    public UserDto getDtoFromUser(){
//        UserDto userDto = new UserDto();
//        userDto.setUsername(this.username);
////        userDto.setPassword(password);
//        userDto.setEmail(this.email);
//
//        return userDto;
//    }


    //*******************************************************

}
