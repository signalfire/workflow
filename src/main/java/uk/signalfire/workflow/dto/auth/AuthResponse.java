package uk.signalfire.workflow.dto.auth;
import uk.signalfire.workflow.model.enums.Role;

public record AuthResponse(
        Long id,
        String token,
        String email,
        String firstName,
        String lastName,
        Role role
) {}
