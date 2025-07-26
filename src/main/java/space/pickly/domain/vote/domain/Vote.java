package space.pickly.domain.vote.domain;

import jakarta.persistence.*;
import lombok.*;
import space.pickly.domain.concern.domain.Concern;
import space.pickly.domain.concern.domain.ConcernItem;
import space.pickly.domain.user.domain.User;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 고민에 대한 투표인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concern_id", nullable = false)
    private Concern concern;

    // 어떤 사용자가 투표했는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    // 어떤 항목에 투표했는지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concern_item_id", nullable = false)
    private ConcernItem concernItem;

    private LocalDateTime createdAt;
}