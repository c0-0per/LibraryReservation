package cz.cvut.ear.privatelib.rest;

import cz.cvut.ear.privatelib.dto.ReviewDto;
import cz.cvut.ear.privatelib.model.Review;
import cz.cvut.ear.privatelib.model.Title;
import cz.cvut.ear.privatelib.model.User;
import cz.cvut.ear.privatelib.service.ReviewService;
import cz.cvut.ear.privatelib.service.TitleService;
import cz.cvut.ear.privatelib.service.UserService;
import cz.cvut.ear.privatelib.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    private final TitleService titleService;
    private final UserService userService;

    @Autowired
    public ReviewController(ReviewService reviewService, TitleService titleService, UserService userService) {
        this.reviewService = reviewService;
        this.titleService = titleService;
        this.userService = userService;
    }


    @GetMapping
    public List<Review> getReviews() {
        return reviewService.findAll();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getAllReviewsByUser(@PathVariable Integer userId) {
        final User user = userService.getUserById(userId);
        List<Review> reviews = reviewService.findAllByUser(user);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/title/{titleId}")
    public ResponseEntity<List<Review>> getAllReviewsByTitle(@PathVariable Integer titleId) {
        final Title title = titleService.getEntityById(titleId);
        List<Review> reviews = reviewService.findAllByTitle(title);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@PathVariable Integer userId, @RequestBody ReviewDto reviewDto) {

        // Verify that the user is authenticated
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must be authenticated to create a review");
        }

        Review review = new Review();

        review.setTitle(titleService.getEntityById(reviewDto.getTitleId())); // предполагается, что у ReviewDto есть поле titleId
        review.setUser(userService.getUserById(userId));

        review.setComment(reviewDto.getComment());
        review.setRating(reviewDto.getRating());

        Review createdReview = reviewService.create(review);

        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

}
