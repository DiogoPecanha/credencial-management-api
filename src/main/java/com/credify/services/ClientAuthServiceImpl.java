package com.credify.services;

import com.credify.domain.model.CredifyToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class ClientAuthServiceImpl implements ClientAuthService {
    @Value("${spring.security.oauth2.client.provider.keycloak.issuer-uri}")
    private String issuerUri;

    private final WebClient webClient;
    private final JwtDecoder jwtDecoder;

    public ClientAuthServiceImpl(WebClient.Builder webClientBuilder, JwtDecoder jwtDecoder) {
        this.webClient = webClientBuilder.build();
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public CredifyToken generateClientToken(String clientId, String clientSecret) {
        String tokenUrl = String.format("%s/protocol/openid-connect/token", issuerUri);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("grant_type", "client_credentials");        
        Map<String, Object> response = webClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .block();

        CredifyToken token = null;
        if (response != null){
            var access_token = response.get("access_token").toString();
            var expiresIn = Long.parseLong(response.get("expires_in").toString());
            var tokenType = response.get("token_type").toString();
            token = new CredifyToken(access_token, tokenType, expiresIn);
        }

        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwt jwt = jwtDecoder.decode(token);
            return jwt != null;
        } catch (JwtValidationException e) {
            return false;
        }
    }
}
