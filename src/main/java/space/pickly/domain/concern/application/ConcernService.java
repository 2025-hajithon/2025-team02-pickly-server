package space.pickly.domain.concern.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import space.pickly.domain.concern.dao.ConcernRepository;
import space.pickly.domain.concern.domain.Concern;
import space.pickly.domain.concern.domain.ConcernStatus;
import space.pickly.domain.concern.dto.ConcernFeedDTO;
import space.pickly.domain.concern.dto.ConcernItemFeedDTO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConcernService {

    private final ConcernRepository concernRepository;

    /**
     * Create a new concern
     */
    @Transactional
    public ConcernFeedDTO createConcern(Concern concern) {
        concern.setCreatedAt(LocalDateTime.now());
        concern.setStatus(ConcernStatus.OPEN);
        concernRepository.save(concern);

        return ConcernConverter.mapToConcernFeedDTO(concern);

    }

    /**
     * Get all concerns by user ID
     */
    public List<ConcernFeedDTO> getMyConcerns(Long userId) {
        List<Concern> concerns = concernRepository.findByAuthorId(userId);
        return mapToConcernFeedDTOs(concerns);
    }

    /**
     * Get concerns by user ID where review is null
     */
    public List<ConcernFeedDTO> getMyConcernsWithoutReview(Long userId) {
        List<Concern> concerns = concernRepository.findByAuthorIdAndReviewIsNull(userId);
        return mapToConcernFeedDTOs(concerns);
    }

    /**
     * Get all concerns with OPEN status
     */
    public List<ConcernFeedDTO> getOngoingConcerns() {
        List<Concern> concerns = concernRepository.findByStatus(ConcernStatus.OPEN);
        return mapToConcernFeedDTOs(concerns);
    }

    /**
     * Map Concern entities to ConcernFeedDTOs
     */
    private List<ConcernFeedDTO> mapToConcernFeedDTOs(List<Concern> concerns) {
        return concerns.stream()
                .map(ConcernConverter::mapToConcernFeedDTO)
                .collect(Collectors.toList());
    }
/*
    *//**
     * Map a Concern entity to a ConcernFeedDTO
     *//*
    private ConcernFeedDTO mapToConcernFeedDTO(Concern concern) {
        // Calculate remaining minutes
        long remainingMinutes = 0;
        if (concern.getExpiresAt() != null) {
            remainingMinutes = Math.max(0, ChronoUnit.MINUTES.between(LocalDateTime.now(), concern.getExpiresAt()));
        }

        // Map concern items
        List<ConcernItemFeedDTO> itemDTOs = concern.getItems().stream()
                .map(item -> {
                    double percent = concern.getTotalParticipants() > 0 
                            ? (double) item.getVoteCount() / concern.getTotalParticipants() * 100 
                            : 0;
                    
                    return ConcernItemFeedDTO.builder()
                            .id(item.getId())
                            .content(item.getContent())
                            .voteCount(item.getVoteCount())
                            .percent(percent)
                            .build();
                })
                .collect(Collectors.toList());


        // Build and return the DTO
        return ConcernFeedDTO.from(concern, itemDTOs);
    }*/
}