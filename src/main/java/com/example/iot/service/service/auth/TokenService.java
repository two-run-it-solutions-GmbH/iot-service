package com.example.iot.service.service.auth;

import com.example.iot.service.client.keycloak.KeycloakClient;
import com.example.iot.service.configuration.openremote.OpenRemoteProperties;
import com.example.iot.service.model.response.auth.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final KeycloakClient keycloakClient;
    private final OpenRemoteProperties properties;

    private TokenResponse cachedToken;
    private Instant tokenExpiresAt;

    public synchronized String getToken() {
        if (isValidToken()) {
            return cachedToken.accessToken();
        }

        Map<String, String> form = Map.of(
                "grant_type", "client_credentials",
                "client_id", properties.getClientId(),
                "client_secret", properties.getClientSecret()
        );

        log.debug("Fetching new token from Keycloak");
        TokenResponse newToken = keycloakClient.getToken(form);

        if (newToken == null || newToken.accessToken() == null) {
            log.error("Failed to fetch access token from Keycloak");
            throw new IllegalStateException("Keycloak token fetch failed");
        }

        cacheToken(newToken);
        return newToken.accessToken();
    }

    private boolean isValidToken() {
        return cachedToken != null
                && tokenExpiresAt != null
                && Instant.now().isBefore(tokenExpiresAt.minusSeconds(60));
    }

    private void cacheToken(TokenResponse token) {
        this.cachedToken = token;
        this.tokenExpiresAt = Instant.now().plusSeconds(token.expiresIn());
    }
}
