package com.example.iot.service.client.keycloak;

import com.example.iot.service.configuration.feign.keycloak.KeycloakClientFeignConfiguration;
import com.example.iot.service.model.response.auth.TokenResponse;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(
        name = "keycloak-client",
        url = "${openremote.base-url}${openremote.keycloak-path}/realms/${openremote.realm}/protocol/openid-connect",
        configuration = KeycloakClientFeignConfiguration.class
)
public interface KeycloakClient {

    @PostMapping(value = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Headers("Content-Type: application/x-www-form-urlencoded")
    TokenResponse getToken(@RequestBody Map<String, ?> form);
}
