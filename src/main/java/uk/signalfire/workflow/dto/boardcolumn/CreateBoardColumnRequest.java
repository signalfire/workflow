package uk.signalfire.workflow.dto.boardcolumn;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateBoardColumnRequest(
    @NotBlank @Size(min=3, max=100, message="Minimum of 3, maximum of 100 characters") String title,
    @NotNull(message="Position is required") Integer position
) {}