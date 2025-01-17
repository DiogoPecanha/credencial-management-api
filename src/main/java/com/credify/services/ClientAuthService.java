package com.credify.services;

import com.credify.domain.model.CredifyToken;

public interface ClientAuthService {
    CredifyToken generateClientToken(String clientId, String clientSecret);
    boolean validateToken(String token);
}
