package cz.cvut.ear.privatelib.dao;

import cz.cvut.ear.privatelib.model.Book;
import cz.cvut.ear.privatelib.model.Cart;
import cz.cvut.ear.privatelib.model.Title;
import cz.cvut.ear.privatelib.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CartDao extends AbstractDao<Cart>{

    public CartDao() {
        super(Cart.class);
    }

    public Cart findByOwner(User owner) {
        try {
            return manager.createQuery("SELECT c FROM Cart c WHERE c.owner = :owner", Cart.class)
                    .setParameter("owner", owner)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Cart> findCartsByOwner(User owner) {
        try {
            return manager.createQuery("SELECT c FROM Cart c WHERE c.owner = :owner", Cart.class)
                    .setParameter("owner", owner)
                    .getResultList();
        } catch (NoResultException e) {
            return new ArrayList<>(); // Return an empty list if no results are found
        }
    }

//    public List<Title> findTitlesByOwner(User owner) {
//        try {
//            // Replace 'items' with the actual collection name in the Cart entity that refers to books
//            // Replace 'book.title' with the actual path to the Title entity in the Book entity
//            return manager.createQuery("SELECT b.title FROM Cart c JOIN c.items b WHERE c.owner = :owner", Title.class)
//                    .setParameter("owner", owner)
//                    .getResultList();
//        } catch (NoResultException e) {
//            return new ArrayList<>(); // Return an empty list if no results are found
//        }
//    }

    public Cart findByOwnerAndBook(User owner, Book book) {
        try {
            return manager.createQuery("SELECT c FROM Cart c WHERE c.owner = :owner AND c.book = :book", Cart.class)
                    .setParameter("owner", owner)
                    .setParameter("book", book)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<Cart> findAllCartsByOwner(User owner) {
        try {
            return manager.createQuery("SELECT c FROM Cart c WHERE c.owner = :owner", Cart.class)
                    .setParameter("owner", owner)
                    .getResultList();
        } catch (NoResultException e) {
            // No carts found for the user
            return new ArrayList<>();
        }
    }

}
