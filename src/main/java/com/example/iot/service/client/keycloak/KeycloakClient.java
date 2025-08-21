package com.example.iot.service.client.keycloak;

import com.example.iot.service.configuration.feign.keycloak.KeycloakFeignConfig;
import com.example.iot.service.model.response.auth.TokenResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        name = "keycloak-client",
        url = "${openremote.keycloak-base-url}",
        configuration = KeycloakFeignConfig.class
)
public interface KeycloakClient {
    @PostMapping(value="/realms/{realm}/protocol/openid-connect/token",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Headers("Content-Type: application/x-www-form-urlencoded")
    TokenResponse getToken(@PathVariable("realm") String realm,
                           @RequestBody Map<String, ?> form);
}
