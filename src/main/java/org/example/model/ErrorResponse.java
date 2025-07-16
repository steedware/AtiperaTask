package org.example.model;

public record ErrorResponse(
        int status,
        String message
) {
}
