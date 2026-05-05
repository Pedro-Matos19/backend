package org.bibliotecaviva.backend.application.services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TokenBlackListServiceTest {

    private final TokenBlackListService tokenBlackListService = new TokenBlackListService();

    @Test
    void invalidateShouldAddTokenToBlacklist() {
        String token = "token-123";

        assertFalse(tokenBlackListService.isBlacklisted(token));

        tokenBlackListService.invalidate(token);

        assertTrue(tokenBlackListService.isBlacklisted(token));
    }

    @Test
    void isBlacklistedShouldReturnFalseForUnknownToken() {
        tokenBlackListService.invalidate("token-123");

        assertFalse(tokenBlackListService.isBlacklisted("other-token"));
    }
}
