package uk.signalfire.workflow.dto.boardcolumn;

import java.time.LocalDateTime;

public record BoardColumnResponse(
    Long id,
    Long boardId,
    String title,
    Integer position,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
