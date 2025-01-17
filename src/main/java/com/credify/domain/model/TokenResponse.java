package com.credify.domain.model;

public record TokenResponse(String accessToken,
                            String tokenType,
                            long expiresIn) {
}
