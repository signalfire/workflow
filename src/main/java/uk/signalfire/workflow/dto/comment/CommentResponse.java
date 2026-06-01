package uk.signalfire.workflow.dto.comment;

import uk.signalfire.workflow.dto.user.UserSummaryResponse;

import java.time.LocalDateTime;

public record CommentResponse(
    Long id,
    Long cardId,
    UserSummaryResponse author,
    String body,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
){}
