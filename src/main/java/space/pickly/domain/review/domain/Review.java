package space.pickly.domain.review.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import space.pickly.domain.concern.domain.Concern;
import space.pickly.domain.concern.domain.ConcernItem;
import space.pickly.domain.user.domain.User;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 어느 고민에 대한 후기인지 (1:1) **/
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concern_id", nullable = false)
    private Concern concern;

    /** 후기를 작성한 사용자 **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private User author;

    /** 후기 본문 **/
    @Column(columnDefinition = "TEXT")
    private String content;

    /** 후기 작성 시각 **/
    private LocalDateTime createdAt;

    /** 해당 후기에 달린 좋아요 수 **/
    private int likeCount;

    /** 최종 선택된 고민 아이템 **/
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "selected_item_id", nullable = false)
    private ConcernItem selectedItem;

    /** 소프트 삭제 플래그 **/
    private boolean isDeleted;
}
