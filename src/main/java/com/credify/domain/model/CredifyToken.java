package com.credify.domain.model;

public record CredifyToken(String accessToken,
                    String tokenType,
                    long expiresIn) {
}
