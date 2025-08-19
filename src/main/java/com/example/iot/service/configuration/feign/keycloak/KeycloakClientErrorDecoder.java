package com.example.iot.service.configuration.feign.keycloak;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeycloakClientErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("KeycloakFeign request failed | Method: {}, Status: {}, Reason: {}",
                methodKey, response.status(), response.reason());
        return defaultDecoder.decode(methodKey, response);
    }
}
