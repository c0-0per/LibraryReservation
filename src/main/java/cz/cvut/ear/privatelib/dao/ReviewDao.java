package cz.cvut.ear.privatelib.dao;

import cz.cvut.ear.privatelib.model.Genre;
import cz.cvut.ear.privatelib.model.Review;
import cz.cvut.ear.privatelib.model.Title;
import cz.cvut.ear.privatelib.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Repository
public class ReviewDao extends AbstractDao<Review> {
    public ReviewDao() {
        super(Review.class);
    }

    public List<Review> findAllByUser(User user){
        Objects.requireNonNull(user);
        return manager.createNamedQuery("Review.findAllByUser", Review.class)
                .setParameter("user", user)
                .getResultList();
    }

    public List<Review> findAllByTitle(Title title) {
        Objects.requireNonNull(title);
        return manager.createNamedQuery("Review.findAllByTitle", Review.class)
                .setParameter("title", title)
                .getResultList();
    }

//    public List<Review> findAllReviews() {
//        return manager.createQuery("SELECT r FROM Review r", Review.class)
//                .getResultList();
//    }

}
