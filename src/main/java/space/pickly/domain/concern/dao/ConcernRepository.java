package space.pickly.domain.concern.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import space.pickly.domain.concern.domain.Concern;
import space.pickly.domain.concern.domain.ConcernStatus;

import java.util.List;

public interface ConcernRepository extends JpaRepository<Concern, Long> {
    // Find all concerns by author ID
    List<Concern> findByAuthorId(Long authorId);
    
    // Find all concerns by author ID and status
    List<Concern> findByAuthorIdAndStatus(Long authorId, ConcernStatus status);
    
    // Find all concerns by status
    List<Concern> findByStatus(ConcernStatus status);
    
    // Find all concerns by author ID where review is null
    List<Concern> findByAuthorIdAndReviewIsNull(Long authorId);
}