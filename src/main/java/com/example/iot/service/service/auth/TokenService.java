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

    // Tek realm için cache (admin için kullanılıyor)
    private TokenResponse cachedToken;
    private Instant tokenExpiresAt;

    public synchronized String getToken() {
        return getTokenForRealm(properties.getAdminRealm()); // adminRealm getter adı doğru olsun
    }

    public synchronized String getTokenForRealm(String realm) {
        if (isValidToken(realm)) {
            return cachedToken.accessToken();
        }

        Map<String, String> form = Map.of(
                "grant_type", "client_credentials",
                "client_id", properties.getClientId(),
                "client_secret", properties.getClientSecret()
        );

        log.debug("Fetching new token from Keycloak for realm={}", realm);
        TokenResponse newToken = keycloakClient.getToken(realm, form);

        if (newToken == null || newToken.accessToken() == null) {
            log.error("Failed to fetch access token from Keycloak for realm={}", realm);
            throw new IllegalStateException("Keycloak token fetch failed for realm=" + realm);
        }

        cacheToken(newToken);
        return newToken.accessToken();
    }

    private boolean isValidToken(String realm) {
        // İleri seviye: her realm için ayrı cache tutabilirsin (Map<realm, TokenResponse>)
        return cachedToken != null
                && tokenExpiresAt != null
                && Instant.now().isBefore(tokenExpiresAt.minusSeconds(60));
    }

    private void cacheToken(TokenResponse token) {
        this.cachedToken = token;
        this.tokenExpiresAt = Instant.now().plusSeconds(token.expiresIn());
    }
}

