package uk.signalfire.workflow.dto.user;

public record UserSummaryResponse(
    Long id,
    String email,
    String firstName,
    String lastName
) {}
