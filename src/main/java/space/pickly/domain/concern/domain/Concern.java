package space.pickly.domain.concern.domain;

import jakarta.persistence.*;
import lombok.*;
import space.pickly.domain.review.domain.Review;
import space.pickly.domain.vote.domain.Vote;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "concerns")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Concern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long authorId;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    @Enumerated(EnumType.STRING)
    private ConcernStatus status;

    private int totalParticipants;

    // 관계 매핑
    @OneToMany(mappedBy = "concern", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ConcernItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "concern", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Vote> votes = new ArrayList<>();

    @OneToOne(mappedBy = "concern", cascade = CascadeType.ALL, orphanRemoval = true)
    private ConcernSetting setting;

    @OneToOne(mappedBy = "concern", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;
}
