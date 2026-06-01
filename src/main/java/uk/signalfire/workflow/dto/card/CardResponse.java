package uk.signalfire.workflow.dto.card;

import uk.signalfire.workflow.dto.tag.TagResponse;
import uk.signalfire.workflow.dto.user.UserSummaryResponse;

import java.time.LocalDateTime;

import java.util.List;

public record CardResponse(
    Long id,
    Long columnId,
    String title,
    String description,
    Integer position,
    List<UserSummaryResponse> assignees,
    List<TagResponse> tags,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
