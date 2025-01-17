package com.credify.services;

public interface ClientAuthService {
    String generateClientToken(String clientId, String clientSecret);
    boolean validateToken(String token);
}
