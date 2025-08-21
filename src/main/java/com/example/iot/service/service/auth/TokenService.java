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

    // Tek cache ama realm'e bağlı
    private TokenResponse cachedToken;
    private Instant tokenExpiresAt;
    private String cachedRealm;

    public synchronized String getToken() {
        return getTokenForRealm(properties.getAdminRealm()); // örn: kasiskafe
    }

    public synchronized String getTokenForRealm(String realm) {
        String r = (realm == null || realm.isBlank()) ? properties.getAdminRealm() : realm;

        if (isValidToken(r)) {
            return cachedToken.accessToken();
        }

        // realm'e göre doğru client kimliği/secret'ı seç
        Map<String, String> form =
                "master".equals(r)
                        ? Map.of(
                        "grant_type", "client_credentials",
                        "client_id", properties.getMasterClientId(),
                        "client_secret", properties.getMasterClientSecret()
                )
                        : Map.of(
                        "grant_type", "client_credentials",
                        "client_id", properties.getTenantClientId(),
                        "client_secret", properties.getTenantClientSecret()
                );

        log.info("Fetching new token from Keycloak for realm={}", r);
        TokenResponse newToken = keycloakClient.getToken(r, form);
        if (newToken == null || newToken.accessToken() == null) {
            throw new IllegalStateException("Keycloak token fetch failed for realm=" + r);
        }

        cacheToken(newToken, r);
        return newToken.accessToken();
    }

    private boolean isValidToken(String realm) {
        return cachedToken != null
                && cachedRealm != null
                && cachedRealm.equals(realm)
                && tokenExpiresAt != null
                && Instant.now().isBefore(tokenExpiresAt.minusSeconds(60));
    }

    private void cacheToken(TokenResponse token, String realm) {
        this.cachedToken = token;
        this.cachedRealm = realm;
        this.tokenExpiresAt = Instant.now().plusSeconds(token.expiresIn());
    }
}

