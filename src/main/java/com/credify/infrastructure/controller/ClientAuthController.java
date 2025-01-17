package com.credify.infrastructure.controller;

import com.credify.domain.model.TokenRequest;
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

    @PostMapping()
    public ResponseEntity<String> generateClientToken(@RequestBody TokenRequest tokenRequest) {
        try {
            String token = clientAuthService.generateClientToken(tokenRequest.clientId(), tokenRequest.clientSecret());
            return ResponseEntity.ok(token);
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        if (clientAuthService.validateToken(token)) {
            return ResponseEntity.ok("Valid Token!");
        } else {
            throw new InvalidTokenException("Expired or invalid Token");
        }
    }
}
