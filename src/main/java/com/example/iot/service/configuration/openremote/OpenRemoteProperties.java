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
    private String apiBaseUrl;
    private String keycloakBaseUrl;
    private String adminRealm;   // dikkat: adminRealm
    private String clientId;
    private String clientSecret;
}
