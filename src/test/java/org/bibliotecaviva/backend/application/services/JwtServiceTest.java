package org.bibliotecaviva.backend.application.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.bibliotecaviva.backend.domain.entities.User;
import org.bibliotecaviva.backend.domain.enums.Role;
import org.bibliotecaviva.backend.domain.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtServiceTest {

    private static final String SECRET = "Y29kZXgtdGVzdC1qd3Qtc2VjcmV0LWZvci1qd3Qtc2VydmljZS10ZXN0cw==";

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", 86_400_000L);
    }

    @Test
    void generateTokenShouldUseUserEmailAsSubjectAndIncludeRoleClaim() {
        User user = buildUser("admin@teste.com", Role.ADMIN);

        String token = jwtService.generateToken(user);

        assertEquals(user.getEmail(), jwtService.extractUsername(token));
        assertEquals(Role.ADMIN.name(), jwtService.extractClaim(token, claims -> claims.get("role", String.class)));
    }

    @Test
    void isTokenValidShouldReturnTrueForMatchingUserAndNonExpiredToken() {
        User user = buildUser("aluno@teste.com", Role.ALUNO);
        String token = jwtService.generateToken(user);

        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    void isTokenValidShouldReturnFalseWhenUsernameDoesNotMatch() {
        User user = buildUser("aluno@teste.com", Role.ALUNO);
        User otherUser = buildUser("outro@teste.com", Role.ALUNO);
        String token = jwtService.generateToken(user);

        assertFalse(jwtService.isTokenValid(token, otherUser));
    }

    @Test
    void isTokenValidShouldFailWhenTokenIsExpired() {
        User user = buildUser("aluno@teste.com", Role.ALUNO);
        ReflectionTestUtils.setField(jwtService, "jwtExpiration", -1_000L);
        String token = jwtService.generateToken(user);

        assertThrows(ExpiredJwtException.class, () -> jwtService.isTokenValid(token, user));
    }

    @Test
    void extractUsernameShouldFailForMalformedToken() {
        assertThrows(JwtException.class, () -> jwtService.extractUsername("not-a-jwt"));
    }

    private static User buildUser(String email, Role role) {
        return User.builder()
                .name("Usuario")
                .email(email)
                .password("123456")
                .role(role)
                .accountStatus(Status.ACTIVE)
                .build();
    }
}
