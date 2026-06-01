package uk.signalfire.workflow.dto.board;

import uk.signalfire.workflow.dto.user.UserSummaryResponse;

import java.time.LocalDateTime;

import java.util.List;

public record BoardResponse(
    Long id,
    List<UserSummaryResponse> members,
    String title,
    UserSummaryResponse owner,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
