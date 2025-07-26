package space.pickly.domain.review.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import space.pickly.domain.review.domain.Review;
import space.pickly.domain.concern.domain.Concern;
import space.pickly.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // Find all reviews by author
    List<Review> findByAuthor(User author);
    
    // Find all reviews by author ID
    List<Review> findByAuthorId(Long authorId);
    
    // Find review by concern
    Review findByConcern(Concern concern);
    
    // Find review by concern ID
    Review findByConcernId(Long concernId);
    
    // Find all reviews (for admin purposes)
    List<Review> findAll();

    Optional<Review> findByConcern_Id(Long concernId);
}