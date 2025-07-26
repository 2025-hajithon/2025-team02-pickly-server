package space.pickly.domain.concern.application;

import org.springframework.stereotype.Component;
import space.pickly.domain.concern.domain.Concern;
import space.pickly.domain.concern.dto.ConcernFeedDTO;
import space.pickly.domain.concern.dto.ConcernItemFeedDTO;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConcernConverter {
    /**
     * Map a Concern entity to a ConcernFeedDTO
     */
    public static ConcernFeedDTO mapToConcernFeedDTO(Concern concern) {
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
    }
}
