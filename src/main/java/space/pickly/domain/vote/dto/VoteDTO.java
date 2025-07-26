package space.pickly.domain.vote.dto;

public record VoteDTO(boolean hasVoted, Long selectedConcernItemId) {
    public static VoteDTO of(boolean hasVoted, Long selectedConcernItemId) {
        return new VoteDTO(hasVoted, selectedConcernItemId);
    }
}
