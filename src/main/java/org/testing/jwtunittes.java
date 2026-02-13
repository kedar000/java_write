package org.testing;

package org.examplemodulespringboot.jwt_task.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    void generateToken_and_extractUsername_shouldWork() {

        String username = "kedar123";

        String token = jwtService.generateToken(username);

        assertNotNull(token);

        String extractedUsername = jwtService.extractUsername(token);

        assertEquals(username, extractedUsername);
    }

    @Test
    void token_shouldBeInvalid_whenTampered() {

        String token = jwtService.generateToken("kedar");

        String tamperedToken = token + "abc";

        assertThrows(Exception.class,
                () -> jwtService.extractUsername(tamperedToken));
    }
}
