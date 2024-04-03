package cz.cvut.ear.privatelib.service;

import cz.cvut.ear.privatelib.dao.ReviewDao;
import cz.cvut.ear.privatelib.model.Review;
import cz.cvut.ear.privatelib.model.Title;
import cz.cvut.ear.privatelib.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ReviewService {
    private final ReviewDao reviewDao;
    private final UserService userService;

    @Autowired
    public ReviewService(ReviewDao reviewDao, UserService userService) {
        this.reviewDao = reviewDao;
        this.userService = userService;
    }

    @Transactional
    public List<Review> findAll(){
        return reviewDao.getAll();
    }

    public List<Review> findAllByUser(User user) {
        return reviewDao.findAllByUser(user);
    }

    public List<Review> findAllByTitle(Title title) {
        return reviewDao.findAllByTitle(title);
    }

    @Transactional
    public Review create(Review review) {
        Objects.requireNonNull(review);

        reviewDao.persist(review);

        User createdBy = review.getUser();
        Title title = review.getTitle();

        Objects.requireNonNull(createdBy);
        Objects.requireNonNull(title);

        createdBy.getReviews().add(review);
        title.getReviews().add(review);

        return review;

    }

}
