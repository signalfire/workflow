package uk.signalfire.workflow.dto.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBoardRequest(
    @NotBlank @Size(min=3, max=100, message="Minimum of 3, maximum of 100 characters") String title
) {}