package org.bibliotecaviva.backend.application.services;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    private final Map<String, Date> blacklistedTokens = new ConcurrentHashMap<>();

    public void blacklist(String token, Date expiresAt) {
        blacklistedTokens.put(token, expiresAt);
    }

    public boolean isBlacklisted(String token) {
        Date expiresAt = blacklistedTokens.get(token);
        if (expiresAt == null) {
            return false;
        }
        if (expiresAt.before(new Date())) {
            blacklistedTokens.remove(token);
            return false;
        }
        return true;
    }
}
