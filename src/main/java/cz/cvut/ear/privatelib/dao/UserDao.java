package cz.cvut.ear.privatelib.dao;

import cz.cvut.ear.privatelib.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;

@Repository
public class UserDao extends AbstractDao<User> {
    public UserDao() {
        super(User.class);
    }


    public User findByUsername(String username) {
        try {
            return (User) manager.createNamedQuery("User.findByUsername")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User save(User user) {
        persist(user);
        return user;
    }

    public int sumNumber(int a, int b){
        return a+b;

    }

}
