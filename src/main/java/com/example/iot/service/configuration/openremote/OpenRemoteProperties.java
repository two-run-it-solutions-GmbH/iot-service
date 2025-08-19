package com.example.iot.service.configuration.openremote;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "openremote")
public class OpenRemoteProperties {
    private String baseUrl;
    private String keycloakPath;
    private String realm;
    private String clientId;
    private String clientSecret;
}
