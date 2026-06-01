package uk.signalfire.workflow.dto.card;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCardRequest(
    @NotBlank @Size(min=3, max=100, message="Minimum of 3, maximum of 100 characters") String title,
    String description,
    @NotNull(message="Position is required") Integer position
) {}