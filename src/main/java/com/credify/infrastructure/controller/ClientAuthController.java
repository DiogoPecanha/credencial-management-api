package com.credify.infrastructure.controller;

import com.credify.domain.model.CredifyToken;
import com.credify.domain.model.TokenRequest;
import com.credify.domain.model.TokenResponse;
import com.credify.domain.model.TokenValidationRequest;
import com.credify.exception.InvalidTokenException;
import com.credify.services.ClientAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientAuthController {
    private final ClientAuthService clientAuthService;

    public ClientAuthController(ClientAuthService clientAuthService) {

        this.clientAuthService = clientAuthService;
    }

    @PostMapping("/generate-token")
    public ResponseEntity<TokenResponse> generateClientToken(@RequestBody TokenRequest tokenRequest) {
        try {
            CredifyToken token = clientAuthService.generateClientToken(tokenRequest.clientId(), tokenRequest.clientSecret());
            return ResponseEntity.ok(new TokenResponse(token.accessToken()));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestBody TokenValidationRequest tokenValidation) {
        if (clientAuthService.validateToken(tokenValidation.token())) {
            return ResponseEntity.ok("Valid Token!");
        } else {
            throw new InvalidTokenException("Expired or invalid Token");
        }
    }
}
